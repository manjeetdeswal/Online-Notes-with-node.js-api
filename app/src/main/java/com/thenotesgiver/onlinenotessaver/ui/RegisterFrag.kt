package com.thenotesgiver.onlinenotessaver.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.thenotesgiver.onlinenotessaver.R
import com.thenotesgiver.onlinenotessaver.databinding.FragmentRegisterBinding
import com.thenotesgiver.onlinenotessaver.models.UserRequest
import com.thenotesgiver.onlinenotessaver.utils.TokenManager
import com.thenotesgiver.onlinenotessaver.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFrag : Fragment() {

private lateinit var binding: FragmentRegisterBinding
private val viewModel by viewModels<AuthViewModel>()
    @Inject
    lateinit var tokenManger :TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =FragmentRegisterBinding.inflate(inflater,container,false)

        if (tokenManger.getToken() != null){
            findNavController().navigate(R.id.action_registerFrag_to_mainragment)
        }


        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
          val result = validateInput()
            if (result.first){
                viewModel.registerUser(getUserReq())

            }else{
                binding.txtError.text = result.second
            }
            // findNavController().navigate(R.id.action_registerFrag_to_mainragment)
        }
        binding.btnLogin.setOnClickListener {

            findNavController().navigate(R.id.action_registerFrag_to_login_frag)
        }



        ViewObserver()
    }
private fun getUserReq(): UserRequest {
    val email = binding.txtEmail.text.toString()
    val username = binding.txtUsername.text.toString()
    val password  = binding.txtPassword.text.toString()
    return UserRequest(email,password,username)
}
    private  fun validateInput(): Pair<Boolean, String> {
        val user =getUserReq()
        return viewModel.validateCred(user.username,user.password,user.email,false)

    }

    private fun ViewObserver() {
        viewModel.resLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManger.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFrag_to_mainragment)
                }
                is NetworkResult.Error -> {

                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

            }
        })
    }
}