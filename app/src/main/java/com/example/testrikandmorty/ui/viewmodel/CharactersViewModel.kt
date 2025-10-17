package com.example.testrikandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.testrikandmorty.data.model.Character
import com.example.testrikandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val _statusFilter = MutableStateFlow<String?>(null)
    val statusFilter = _statusFilter.asStateFlow()
    
    private val _speciesFilter = MutableStateFlow<String?>(null)
    val speciesFilter = _speciesFilter.asStateFlow()
    
    private val _genderFilter = MutableStateFlow<String?>(null)
    val genderFilter = _genderFilter.asStateFlow()
    
    val characters: Flow<PagingData<Character>> = combine(
        _searchQuery,
        _statusFilter,
        _speciesFilter,
        _genderFilter
    ) { query, status, species, gender ->
        SearchParams(
            name = if (query.isBlank()) null else query,
            status = status,
            species = species,
            gender = gender
        )
    }
        .debounce(300) // Задержка 300мс для оптимизации поиска
        .distinctUntilChanged()
        .flatMapLatest { params ->
            repository.getCharacters(
                name = params.name,
                status = params.status,
                species = params.species,
                gender = params.gender
            )
        }
        .cachedIn(viewModelScope)
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        
        // Автоматически применяем фильтры на основе поискового запроса
        if (query.isNotBlank()) {
            val lowerQuery = query.lowercase()
            
            // Поиск по статусу
            when (lowerQuery) {
                "живой", "alive", "жив" -> _statusFilter.value = "alive"
                "мертвый", "dead", "мертв" -> _statusFilter.value = "dead"
                "неизвестно", "unknown" -> _statusFilter.value = "unknown"
            }
            
            // Поиск по полу
            when (lowerQuery) {
                "мужской", "male", "мужчина" -> _genderFilter.value = "male"
                "женский", "female", "женщина" -> _genderFilter.value = "female"
                "бесполый", "genderless" -> _genderFilter.value = "genderless"
            }
            
            // Поиск по виду
            when (lowerQuery) {
                "человек", "human" -> _speciesFilter.value = "human"
                "инопланетянин", "alien" -> _speciesFilter.value = "alien"
                "гуманоид", "humanoid" -> _speciesFilter.value = "humanoid"
                "мифологический", "mythological" -> _speciesFilter.value = "mythological"
                "животное", "animal" -> _speciesFilter.value = "animal"
                "робот", "robot" -> _speciesFilter.value = "robot"
            }
        }
    }
    
    fun updateStatusFilter(status: String?) {
        _statusFilter.value = status
    }
    
    fun updateSpeciesFilter(species: String?) {
        _speciesFilter.value = species
    }
    
    fun updateGenderFilter(gender: String?) {
        _genderFilter.value = gender
    }
    
    fun clearFilters() {
        _searchQuery.value = ""
        _statusFilter.value = null
        _speciesFilter.value = null
        _genderFilter.value = null
    }
    
    fun clearSearch() {
        _searchQuery.value = ""
    }
}

data class SearchParams(
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?
)
