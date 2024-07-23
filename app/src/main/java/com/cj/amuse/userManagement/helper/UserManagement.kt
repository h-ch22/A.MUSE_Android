package com.cj.amuse.userManagement.helper

import com.cj.amuse.frameworks.helper.AES256Util
import com.cj.amuse.userManagement.models.UserInfoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UserManagement {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    companion object {
        var userInfo: UserInfoModel? = null
    }

    fun signIn(email: String, password: String, completion: (Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    getUserInfo {
                        completion(it)
                        return@getUserInfo
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

    fun signUp(email: String, password: String, name: String, nickName: String, phoneNumber: String, birthday: String, completion: (Boolean) -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    db.collection("Users").document(auth.currentUser?.uid ?: "")
                        .set(
                            hashMapOf(
                                "email" to AES256Util.encrypt(email),
                                "name" to AES256Util.encrypt(name),
                                "nickName" to AES256Util.encrypt(nickName),
                                "phoneNumber" to AES256Util.encrypt(phoneNumber),
                                "birthday" to AES256Util.encrypt(birthday)
                            )
                        )
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                getUserInfo {
                                    completion(it)
                                    return@getUserInfo
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
                } else{
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

    fun sendPasswordResetMail(email: String, completion: (Boolean) -> Unit){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                completion(it.isSuccessful)
            }
            .addOnFailureListener {
                it.printStackTrace()
                completion(false)
                return@addOnFailureListener
            }
    }

    private fun getUserInfo(completion: (Boolean) -> Unit){
        if(auth.currentUser == null){
            completion(false)
            return
        }

        db.collection("Users").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val document = it.result

                    if(document != null && document.exists()){
                        val name = document.get("name") as? String ?: ""
                        val email = document.get("email") as? String ?: ""
                        val nickName = document.get("nickName") as? String ?: ""
                        val phoneNumber = document.get("phoneNumber") as? String ?: ""
                        val birthday = document.get("birthday") as? String ?: ""

                        userInfo = UserInfoModel(
                            uid = document.id,
                            email = AES256Util.decrypt(email),
                            name = AES256Util.decrypt(name),
                            nickName = AES256Util.decrypt(nickName),
                            phoneNumber = AES256Util.decrypt(phoneNumber),
                            birthday = AES256Util.decrypt(birthday)
                        )

                        completion(true)
                        return@addOnCompleteListener
                    } else {
                        completion(false)
                        return@addOnCompleteListener
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