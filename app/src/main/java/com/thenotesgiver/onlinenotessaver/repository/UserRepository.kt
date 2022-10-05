package com.thenotesgiver.onlinenotessaver.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.thenotesgiver.onlinenotessaver.api.UserApi
import com.thenotesgiver.onlinenotessaver.models.UserRequest
import com.thenotesgiver.onlinenotessaver.models.UserResponse
import com.thenotesgiver.onlinenotessaver.ui.NetworkResult

import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    private val _userLivaData = MutableLiveData<NetworkResult<UserResponse>>()

    val userLivaData: LiveData<NetworkResult<UserResponse>>
        get() = _userLivaData

    suspend fun registerUser(userRequest: UserRequest) {
        _userLivaData.value = NetworkResult.Loading()
        val response = userApi.signUp(userRequest)
        handleResponse(response)
    }


    suspend fun LoginUser(userRequest: UserRequest) {
        _userLivaData.value = NetworkResult.Loading()
        val response = userApi.signIn(userRequest)
        handleResponse(response)
    }


    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userLivaData.value = NetworkResult.Success(response.body()!!)
        } else if (response.body() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userLivaData.value = NetworkResult.Error(errorObj.getString("message"))
        } else {
            _userLivaData.value = NetworkResult.Error("Error found" + response.message())
        }
    }
}