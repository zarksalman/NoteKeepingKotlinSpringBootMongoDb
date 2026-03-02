package com.shcoding.notes_app_with_mongo_db.database.repository

import com.shcoding.notes_app_with_mongo_db.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun findByEmail(email:String):User?
}