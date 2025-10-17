package com.example.testrikandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testrikandmorty.data.api.RickAndMortyApi
import com.example.testrikandmorty.data.database.CharacterDao
import com.example.testrikandmorty.data.model.Character
import com.example.testrikandmorty.data.paging.CharacterPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao
) {
    
    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    api = api,
                    characterDao = characterDao,
                    name = name,
                    status = status,
                    species = species,
                    gender = gender
                )
            }
        ).flow
    }
    
    suspend fun getCharacterById(id: Int): Character? {
        return characterDao.getCharacterById(id)
    }
}



