package com.shcoding.notes_app_with_mongo_db.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    @Id val id: ObjectId = ObjectId(),
    val email:String,
    val hashedPassword: String
)
