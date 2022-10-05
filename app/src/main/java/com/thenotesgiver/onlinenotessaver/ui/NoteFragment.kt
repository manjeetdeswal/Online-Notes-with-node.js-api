package com.thenotesgiver.onlinenotessaver.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.thenotesgiver.onlinenotessaver.R
import com.thenotesgiver.onlinenotessaver.databinding.FragmentNoteBinding
import com.thenotesgiver.onlinenotessaver.models.NoteRequest
import com.thenotesgiver.onlinenotessaver.models.NoteResponse
import com.thenotesgiver.onlinenotessaver.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteFragment : Fragment() {
private lateinit var binding: FragmentNoteBinding
private val viewmodel : NoteViewModel by viewModels()
private var noteResponse :NoteResponse ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialState()
        buttonHandling()
        statusObserver()
    }

    private fun statusObserver() {
        viewmodel.statusLiveData.observe(viewLifecycleOwner, Observer {

            when(it){
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                     findNavController().popBackStack()
                }
            }
        })
    }

    private fun buttonHandling() {
        binding.btnDelete.setOnClickListener {
            noteResponse?.let {
                viewmodel.deleteNote(noteid = noteResponse!!._id)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val des = binding.txtDescription.text.toString()
            if (TextUtils.isEmpty(title) &&TextUtils.isEmpty(des)){
                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
            }else{
                if (noteResponse == null){
                    viewmodel.createNote(NoteRequest(title,des))
                }else{
                    viewmodel.updateNote(noteResponse!!._id, NoteRequest(title,des))
                }
            }
        }
    }

    private fun setInitialState(){
        val note = arguments?.getString("note")

        if (note != null){
            noteResponse = Gson().fromJson(note,NoteResponse::class.java)
            noteResponse?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)

            }

        }else{
            binding.addEditText.text = "Add Note"
        }
    }

}