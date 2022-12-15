package lt.timofey.finalprojectandroid.api

import lt.timofey.finalprojectandroid.db.Car
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


val BASE_URL = "https://timofeylyadinsky.github.io/auto_api/"


interface ApiService {

    @GET("api_auto.json")
    fun getCars(): Call<List<Car>>

    companion object Factory{
        fun create(): ApiService{
            val okHttpClient = OkHttpClient.Builder()
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}