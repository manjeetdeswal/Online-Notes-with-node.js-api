package com.thenotesgiver.onlinenotessaver.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thenotesgiver.onlinenotessaver.api.NotesApi
import com.thenotesgiver.onlinenotessaver.models.NoteRequest
import com.thenotesgiver.onlinenotessaver.models.NoteResponse
import com.thenotesgiver.onlinenotessaver.ui.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NotesRepo @Inject constructor(private  val notesApi: NotesApi) {


private val _notesLiveDate =MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val  notesLiveDate :LiveData<NetworkResult<List<NoteResponse>>>
    get() = _notesLiveDate

    private val _statusLiveDate =MutableLiveData<NetworkResult<String>>()
    val  statusLiveDate :LiveData<NetworkResult<String>>
        get() = _statusLiveDate

    suspend fun getNotes(){
         _notesLiveDate.postValue(NetworkResult.Loading())
        val response = notesApi.getNotes()

        if (response.isSuccessful && response.body() != null) {
            _notesLiveDate.value = NetworkResult.Success(response.body()!!)
        } else if (response.body() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveDate.value = NetworkResult.Error(errorObj.getString("message"))
        } else {
            _notesLiveDate.value = NetworkResult.Error("Error found" + response.message())
        }
    }




    suspend fun createNote(noteRequest: NoteRequest){

        _statusLiveDate.value = NetworkResult.Loading()
        val response =notesApi.setNotes(noteRequest)
        handleRespnose(response,"Notes Created")

    }



    suspend fun deleteNote(noteid: String){
        _statusLiveDate.value = NetworkResult.Loading()
        val response = notesApi.deleteNote(noteid)
        handleRespnose(response,"Note Deleted")

    }


    suspend fun updateNote(noteid: String,noteRequest: NoteRequest){

        _statusLiveDate.value = NetworkResult.Loading()
        val response = notesApi.updateNote(noteid,noteRequest)
        handleRespnose(response,"Note Updated")
    }





    private fun handleRespnose(response: Response<NoteResponse>,msg:String) {
        if (response.isSuccessful && !response.body()!!.equals(null)) {
            _statusLiveDate.value = NetworkResult.Success(msg)
        } else {
            _statusLiveDate.value = NetworkResult.Error("Error occurred")
        }
    }

}