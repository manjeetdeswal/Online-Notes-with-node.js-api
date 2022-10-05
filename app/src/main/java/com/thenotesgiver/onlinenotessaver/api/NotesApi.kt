package com.thenotesgiver.onlinenotessaver.api

import androidx.room.Update
import com.thenotesgiver.onlinenotessaver.models.NoteRequest
import com.thenotesgiver.onlinenotessaver.models.NoteResponse
import retrofit2.Response
import retrofit2.http.*

interface NotesApi {
    @GET("/note")
   suspend fun getNotes() : Response<List<NoteResponse>>

    @POST("/note")
   suspend fun setNotes(@Body noteRequest: NoteRequest) :Response<NoteResponse>

   @PUT("/note/{noteid}")
   suspend fun updateNote(@Path("noteid") noteid :String, @Body noteRequest: NoteRequest) : Response<NoteResponse>

   @DELETE ("/note/{noteid}")
   suspend fun deleteNote(@Path("noteid") noteid :String):Response<NoteResponse>
}
