package com.thenotesgiver.onlinenotessaver.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.thenotesgiver.onlinenotessaver.R
import com.thenotesgiver.onlinenotessaver.api.NotesApi
import com.thenotesgiver.onlinenotessaver.databinding.FragmentMainragmentBinding
import com.thenotesgiver.onlinenotessaver.databinding.FragmentRegisterBinding
import com.thenotesgiver.onlinenotessaver.models.NoteRequest
import com.thenotesgiver.onlinenotessaver.models.NoteResponse
import com.thenotesgiver.onlinenotessaver.utils.Constant.TAG
import com.thenotesgiver.onlinenotessaver.utils.NoteAdapter
import com.thenotesgiver.onlinenotessaver.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.addHeaderLenient
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

private lateinit var binding: FragmentMainragmentBinding

private val viewModel :NoteViewModel by viewModels()
private lateinit var noteAdapter: NoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainragmentBinding.inflate(inflater ,container,false)
        noteAdapter = NoteAdapter(::onNoteClicked)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()
        binding.noteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter =noteAdapter

        binding.addNote.setOnClickListener {
           findNavController().navigate(R.id.action_mainragment_to_noteFragment)
        }
        obeserverMain()

    }

    private fun  obeserverMain(){
        viewModel.noteLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible =false

            when(it){
                is NetworkResult.Success ->{

                    noteAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                  binding.progressBar.isVisible =true
                }
            }
        })

    }
    private fun onNoteClicked(noteResponse: NoteResponse ){

        val bundle = Bundle()
        bundle.putString("note",Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_mainragment_to_noteFragment,bundle)

    }

}