package com.cj.amuse.home.helper

import androidx.compose.runtime.mutableStateListOf
import com.cj.amuse.home.models.BannerDataModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class BannersHelper {
    companion object{
        var bannersList = mutableStateListOf<BannerDataModel>()
    }

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getBanners(completion: (Boolean) -> Unit){
        bannersList.clear()

        db.collection("Banners").document("BannerInfo")
            .get().addOnCompleteListener {
                if(it.isSuccessful){
                    val document = it.result

                    if(document != null && document.exists()){
                        val bannerCount = document.get("BannerCount") as? Long ?: 0L

                        for(i in 0 until bannerCount){
                            db.collection("Banners").document("Banner_${i}").get().addOnCompleteListener {
                                if(it.isSuccessful){
                                    val bannerDoc = it.result

                                    if(bannerDoc != null && bannerDoc.exists()){
                                        val author = bannerDoc.get("author") as? String ?: ""
                                        val sentence = bannerDoc.get("sentence") as? String ?: ""
                                        val title = bannerDoc.get("title") as? String ?: ""
                                        val bannerRef = storage.reference.child("/banners/banner_${i}.png")

                                        bannerRef.downloadUrl.addOnCompleteListener {
                                            bannersList.add(
                                                BannerDataModel(
                                                    i.toInt(), it.result, title, author, sentence
                                                )
                                            )
                                        }.addOnFailureListener {
                                            it.printStackTrace()
                                        }
                                    }
                                } else {
                                    completion(false)
                                    return@addOnCompleteListener
                                }
                            }.addOnFailureListener {
                                it.printStackTrace()
                                completion(false)
                                return@addOnFailureListener
                            }
                        }

                        completion(true)
                        return@addOnCompleteListener
                    }
                } else {
                    completion(false)
                    return@addOnCompleteListener
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                completion(false)
                return@addOnFailureListener
            }
    }
}