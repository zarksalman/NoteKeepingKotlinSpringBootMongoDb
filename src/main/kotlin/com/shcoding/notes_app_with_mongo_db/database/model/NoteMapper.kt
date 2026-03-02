package com.shcoding.notes_app_with_mongo_db.database.model

import com.shcoding.notes_app_with_mongo_db.controller.NoteController.NoteRequest
import com.shcoding.notes_app_with_mongo_db.controller.NoteController.NoteResponse
import org.bson.types.ObjectId
import java.time.Instant

fun NoteRequest.toNote(ownerId: String?) = Note(
        // if id is null then return randomize id and call save otherwise update
        id = id?.let { ObjectId(it) } ?: ObjectId.get(),
        title = title,
        content = content,
        color = color,
        createdAt = Instant.now(),
        ownerId = ObjectId(ownerId)
    )

fun Note.toNoteResponse() = NoteResponse(
    id = id.toHexString(),
    title = title,
    content = content,
    color = color,
    createdAt = createdAt
)