package com.shcoding.notes_app_with_mongo_db

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotesAppWithMongoDbApplication

fun main(args: Array<String>) {
	runApplication<NotesAppWithMongoDbApplication>(*args)
}
