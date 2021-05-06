package com.anteeone.coverit.domain.models

data class User(
    val id: String="",
    val name: String = "",
    val age: Long = 0,
    val sex: String = "",
    val role: String = "",
    val about: String = "",
    val likes: List<String> = emptyList(),
    val dislikes: List<String> = emptyList()
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
        return result
    }
}


