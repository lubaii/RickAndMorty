package com.example.testrikandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.testrikandmorty.data.model.Character

@Dao
interface CharacterDao {
    
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int, Character>
    
    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%'")
    fun searchCharacters(query: String): PagingSource<Int, Character>
    
    @Query("SELECT * FROM characters WHERE status = :status")
    fun getCharactersByStatus(status: String): PagingSource<Int, Character>
    
    @Query("SELECT * FROM characters WHERE species = :species")
    fun getCharactersBySpecies(species: String): PagingSource<Int, Character>
    
    @Query("SELECT * FROM characters WHERE gender = :gender")
    fun getCharactersByGender(gender: String): PagingSource<Int, Character>
    
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): Character?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)
    
    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}
