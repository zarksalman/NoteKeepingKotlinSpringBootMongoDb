package com.shcoding.notes_app_with_mongo_db.controller

import com.shcoding.notes_app_with_mongo_db.database.model.toNote
import com.shcoding.notes_app_with_mongo_db.database.model.toNoteResponse
import com.shcoding.notes_app_with_mongo_db.database.repository.NoteRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteRepository: NoteRepository
) {

    data class NoteRequest(
        val id: String?,
        @field:NotBlank(message = "Title could not be blank or empty")
        val title: String,
        val content: String,
        val color: Long
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant
    )

    @PostMapping
    fun saveNote(
        @Valid @RequestBody noteBody: NoteRequest
    ): NoteResponse {
        val ownerId: String = SecurityContextHolder.getContext().authentication.principal as String

        val note = noteRepository.save(
            noteBody.toNote(ownerId)
        )

        return note.toNoteResponse()
    }

    @GetMapping
    fun findByOwnerId(): List<NoteResponse> {
        val ownerId: String = SecurityContextHolder.getContext().authentication.principal as String
        return noteRepository.findByOwnerId(ObjectId(ownerId)).map { it.toNoteResponse() }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val ownerId: String = SecurityContextHolder.getContext().authentication.principal as String

        val note = noteRepository.findById(ObjectId(id)).orElseThrow{
            IllegalArgumentException("Note not found.")
        }

        if (note.ownerId.toHexString() == ownerId){
            noteRepository.deleteById(ObjectId(id))
        }
    }
}