package com.example.testrikandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testrikandmorty.data.api.RickAndMortyApi
import com.example.testrikandmorty.data.database.CharacterDao
import com.example.testrikandmorty.data.model.Character
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val gender: String? = null
) : PagingSource<Int, Character>() {
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1
            val response = api.getCharacters(
                page = page,
                name = name,
                status = status,
                species = species,
                gender = gender
            )
            
            if (response.isSuccessful) {
                val responseBody = response.body()
                val characters = responseBody?.results ?: emptyList()
                
                // Сохраняем в базу данных для офлайн режима только если есть данные
                if (characters.isNotEmpty()) {
                    characterDao.insertCharacters(characters)
                }
                
                LoadResult.Page(
                    data = characters,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (responseBody?.info?.next == null) null else page + 1
                )
            } else {
                // Если запрос не удался (например, 404 для несуществующих фильтров)
                // Возвращаем пустую страницу вместо ошибки
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: IOException) {
            // При отсутствии интернета загружаем из базы данных
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}


