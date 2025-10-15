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
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
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
    
    val characters: Flow<PagingData<Character>> = _searchQuery
        .flatMapLatest { query ->
            repository.getCharacters(
                name = if (query.isBlank()) null else query,
                status = _statusFilter.value,
                species = _speciesFilter.value,
                gender = _genderFilter.value
            )
        }
        .cachedIn(viewModelScope)
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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
}
