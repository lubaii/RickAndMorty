package com.example.testrikandmorty.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testrikandmorty.data.model.Character
import com.example.testrikandmorty.ui.components.CharacterCard
import com.example.testrikandmorty.ui.components.FilterBottomSheet
import com.example.testrikandmorty.ui.viewmodel.CharactersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val statusFilter by viewModel.statusFilter.collectAsState()
    val speciesFilter by viewModel.speciesFilter.collectAsState()
    val genderFilter by viewModel.genderFilter.collectAsState()
    
    var showFilterSheet by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Поиск персонажей...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Поиск")
            },
            trailingIcon = {
                IconButton(onClick = { showFilterSheet = true }) {
                    Icon(Icons.Default.Settings, contentDescription = "Фильтры")
                }
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Active filters
        if (statusFilter != null || speciesFilter != null || genderFilter != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                statusFilter?.let { status ->
                    FilterChip(
                        onClick = { viewModel.updateStatusFilter(null) },
                        label = { Text("Статус: $status") },
                        selected = true
                    )
                }
                speciesFilter?.let { species ->
                    FilterChip(
                        onClick = { viewModel.updateSpeciesFilter(null) },
                        label = { Text("Вид: $species") },
                        selected = true
                    )
                }
                genderFilter?.let { gender ->
                    FilterChip(
                        onClick = { viewModel.updateGenderFilter(null) },
                        label = { Text("Пол: $gender") },
                        selected = true
                    )
                }
                TextButton(onClick = viewModel::clearFilters) {
                    Text("Очистить все")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Characters Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(characters.itemCount) { index ->
                val character = characters[index]
                if (character != null) {
                    CharacterCard(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
                    )
                }
            }
        }
    }
    
    // Filter Bottom Sheet
    if (showFilterSheet) {
        FilterBottomSheet(
            onDismiss = { showFilterSheet = false },
            onApplyFilters = { status, species, gender ->
                viewModel.updateStatusFilter(status)
                viewModel.updateSpeciesFilter(species)
                viewModel.updateGenderFilter(gender)
                showFilterSheet = false
            },
            currentStatus = statusFilter,
            currentSpecies = speciesFilter,
            currentGender = genderFilter
        )
    }
}
