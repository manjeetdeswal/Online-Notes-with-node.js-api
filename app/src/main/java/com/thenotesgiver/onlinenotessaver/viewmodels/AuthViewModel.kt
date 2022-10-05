package com.thenotesgiver.onlinenotessaver.viewmodels

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenotesgiver.onlinenotessaver.models.UserRequest
import com.thenotesgiver.onlinenotessaver.models.UserResponse
import com.thenotesgiver.onlinenotessaver.repository.UserRepository
import com.thenotesgiver.onlinenotessaver.ui.NetworkResult
import com.thenotesgiver.onlinenotessaver.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
    val resLiveData :LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userLivaData


     fun registerUser(userRequest: UserRequest){
         viewModelScope.launch {
             userRepository.registerUser(userRequest)
         }

    }


     fun loginUser(userRequest: UserRequest){

         viewModelScope.launch {
             userRepository.LoginUser(userRequest)
         }

    }


    fun validateCred(username:String ,password:String,email:String,isLogin :Boolean) :Pair<Boolean,String>{
        var result = Pair(true,"")
        if ( !isLogin && TextUtils.isEmpty(username)  || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
              result = Pair(false,"Please input all details")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false,"Please input correct email")
        }
        else if (password.length <6){
            result = Pair(false,"Please enter strong password")
        }
       return result

    }
}