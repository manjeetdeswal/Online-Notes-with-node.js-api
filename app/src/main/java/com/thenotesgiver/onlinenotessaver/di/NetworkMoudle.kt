package com.thenotesgiver.onlinenotessaver.di

import com.thenotesgiver.onlinenotessaver.api.AuthInterceptor
import com.thenotesgiver.onlinenotessaver.api.NotesApi
import com.thenotesgiver.onlinenotessaver.api.UserApi
import com.thenotesgiver.onlinenotessaver.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkMoudle {


    @Singleton
    @Provides
  fun provideRetrofit(): Retrofit.Builder{

      return  Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BASE_URL)

  }
    @Singleton
    @Provides
    fun provideOkhttp(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addNetworkInterceptor(authInterceptor).build()
    }





    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit.Builder) :UserApi{

       return  retrofit.build().create(UserApi::class.java)
    }


    @Singleton
    @Provides
    fun provideNotesApi(retrofit: Retrofit.Builder ,okHttpClient: OkHttpClient) :NotesApi{
        return  retrofit.client(okHttpClient).build().create(NotesApi::class.java)
    }
}