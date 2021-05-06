package com.anteeone.coverit.domain.models

val DEFAULT_AVATAR_URI = "https://i.insider.com/602ee9ced3ad27001837f2ac"

data class User(
    val id: String="",
    val name: String = "",
    val age: Long = 0,
    val sex: String = "",
    val role: String = "",
    val about: String = "",
    val likes: List<String> = emptyList(),
    val dislikes: List<String> = emptyList(),
    val avatarUri: String = DEFAULT_AVATAR_URI
){
    fun toMap(): HashMap<String,Any>
        = hashMapOf(
            Pair("id", this.id),
            Pair("name", this.name),
            Pair("likes", likes),
            Pair("dislikes", dislikes)
        )

    fun toMapForUpdate(): HashMap<String,Any>{
        val result = hashMapOf<String,Any>()
        if(this.name != "") result["name"] = this.name
        if(this.age > 0) result["age"] = this.age
        if(this.role != "") result["role"] = this.role
        if(this.sex != "") result["sex"] = this.sex
        if(this.about != "") result["about"] = this.about
        if(this.avatarUri != DEFAULT_AVATAR_URI) result["avatarUri"] = this.avatarUri
        return result
    }
}


