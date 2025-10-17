package com.example.testrikandmorty.data.api

import com.example.testrikandmorty.data.model.Character
import com.example.testrikandmorty.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Response<CharacterResponse>
    
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>
}



