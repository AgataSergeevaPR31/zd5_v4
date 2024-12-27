package com.example.hateandroidstudio
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("api/books")
    fun getBookInfo(@Query("bibkeys") bibkeys: String, @Query("format") format: String = "json"): Call<BookResponse>
}