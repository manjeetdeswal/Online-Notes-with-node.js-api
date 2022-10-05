package com.thenotesgiver.onlinenotessaver.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenotesgiver.onlinenotessaver.models.NoteRequest
import com.thenotesgiver.onlinenotessaver.repository.NotesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesRepo: NotesRepo) : ViewModel() {


    val noteLiveData = notesRepo.notesLiveDate
    val statusLiveData = notesRepo.statusLiveDate

    fun getNotes() {
        viewModelScope.launch {
            notesRepo.getNotes()
        }
    }

    fun createNote(noteRequest: NoteRequest) {

        viewModelScope.launch {
            notesRepo.createNote(noteRequest)
        }
    }

    fun deleteNote(noteid :String) {

        viewModelScope.launch {
            notesRepo.deleteNote(noteid)
        }
    }

    fun updateNote(noteid :String,noteRequest: NoteRequest) {

        viewModelScope.launch {
            notesRepo.updateNote(noteid,noteRequest)
        }
    }

}