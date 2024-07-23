package com.cj.amuse.contents.helper

import android.net.Uri
import android.util.Log
import com.cj.amuse.contents.models.ContentsDataModel
import com.cj.amuse.contents.models.ContentsTypeModel
import com.cj.amuse.frameworks.helper.AES256Util
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ContentsHelper {
    companion object {
        var bookContents = mutableListOf<ContentsDataModel>()
        var movieContents = mutableListOf<ContentsDataModel>()
        var cultureContents = mutableListOf<ContentsDataModel>()
        var etcContents = mutableListOf<ContentsDataModel>()

        fun getRankSuffix(rank: Int): String {
            when(rank){
                1 -> return "ST"
                2 -> return "ND"
                3 -> return "RD"
                else -> return "TH"
            }
        }
    }

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private fun clearAllList() {
        bookContents.clear()
        movieContents.clear()
        cultureContents.clear()
        etcContents.clear()
    }

    fun getContents(type: ContentsTypeModel, completion: (Boolean) -> Unit) {
        clearAllList()

        db.collection("Contents").whereEqualTo("type", AES256Util.encrypt(type.getString()))
            .addSnapshotListener { value, error ->
                if (error != null) {
                    error.printStackTrace()
                    completion(false)
                    return@addSnapshotListener
                }

                if (value == null) {
                    completion(true)
                    return@addSnapshotListener
                }

                if (value.documents.isEmpty()){
                    completion(true)
                    return@addSnapshotListener
                }

                value.documents.forEach { document ->
                    val author = AES256Util.decrypt(document.get("author") as? String ?: "")
                    val publisher = AES256Util.decrypt(document.get("publisher") as? String ?: "")
                    val summary = AES256Util.decrypt(document.get("summary") as? String ?: "")
                    val title = AES256Util.decrypt(document.get("title") as? String ?: "")
                    val url = AES256Util.decrypt(document.get("url") as? String ?: "")
                    val score = document.get("score") as? Long ?: 0L

                    storage.reference.child("/contents/${type.getString()}/${document.id}.png")
                        .downloadUrl.addOnCompleteListener {
                            when (type) {
                                ContentsTypeModel.BOOK -> {
                                    bookContents.add(
                                        ContentsDataModel(
                                            author = author,
                                            publisher = publisher,
                                            summary = summary,
                                            title = title,
                                            url = Uri.parse(url),
                                            type = type,
                                            cover = it.result,
                                            score = score.toInt()
                                        )
                                    )

                                    bookContents.sortBy {
                                        it.score
                                    }

                                    if(bookContents.size == value.documents.size){
                                        completion(true)
                                        return@addOnCompleteListener
                                    }
                                }

                                ContentsTypeModel.MOVIE -> {
                                    movieContents.add(
                                        ContentsDataModel(
                                            author = author,
                                            publisher = publisher,
                                            summary = summary,
                                            title = title,
                                            url = Uri.parse(url),
                                            type = type,
                                            cover = it.result,
                                            score = score.toInt()
                                        )
                                    )

                                    movieContents.sortBy {
                                        it.score
                                    }

                                    if(movieContents.size == value.documents.size){
                                        completion(true)
                                        return@addOnCompleteListener
                                    }
                                }

                                ContentsTypeModel.CULTURE -> {
                                    cultureContents.add(
                                        ContentsDataModel(
                                            author = author,
                                            publisher = publisher,
                                            summary = summary,
                                            title = title,
                                            url = Uri.parse(url),
                                            type = type,
                                            cover = it.result,
                                            score = score.toInt()
                                        )
                                    )

                                    cultureContents.sortBy {
                                        it.score
                                    }

                                    if(cultureContents.size == value.documents.size){
                                        completion(true)
                                        return@addOnCompleteListener
                                    }
                                }

                                ContentsTypeModel.ETC -> {
                                    etcContents.add(
                                        ContentsDataModel(
                                            author = author,
                                            publisher = publisher,
                                            summary = summary,
                                            title = title,
                                            url = Uri.parse(url),
                                            type = type,
                                            cover = it.result,
                                            score = score.toInt()
                                        )
                                    )

                                    etcContents.sortBy {
                                        it.score
                                    }

                                    if(etcContents.size == value.documents.size){
                                        completion(true)
                                        return@addOnCompleteListener
                                    }
                                }
                            }
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            completion(false)
                            return@addOnFailureListener
                        }
                }
            }
    }
}