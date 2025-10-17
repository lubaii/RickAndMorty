package com.example.testrikandmorty.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testrikandmorty.data.model.Character
import com.example.testrikandmorty.ui.components.CharacterCard
import com.example.testrikandmorty.ui.components.FilterBottomSheet
import com.example.testrikandmorty.ui.viewmodel.CharactersViewModel

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
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
    var isSearchExpanded by remember { mutableStateOf(false) }
    
    val pullRefreshState = rememberPullRefreshState(
        refreshing = characters.loadState.refresh is androidx.paging.LoadState.Loading,
        onRefresh = { characters.refresh() }
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchExpanded) {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = viewModel::updateSearchQuery,
                            onSearch = { isSearchExpanded = false },
                            active = isSearchExpanded,
                            onActiveChange = { isSearchExpanded = it }
                        )
                    } else {
                        Text("Рик и Морти")
                    }
                },
                actions = {
                    if (isSearchExpanded) {
                        IconButton(onClick = { 
                            isSearchExpanded = false
                            viewModel.updateSearchQuery("")
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Закрыть поиск")
                        }
                    } else {
                        IconButton(onClick = { isSearchExpanded = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Поиск")
                        }
                        IconButton(onClick = { showFilterSheet = true }) {
                            Icon(Icons.Default.Settings, contentDescription = "Фильтры")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Active filters
            if (statusFilter != null || speciesFilter != null || genderFilter != null) {
                // Первая строка с фильтрами (статус и вид)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statusFilter?.let { status ->
                        FilterChip(
                            onClick = { viewModel.updateStatusFilter(null) },
                            label = { Text("Статус: $status") },
                            selected = true,
                            modifier = Modifier.height(42.dp)
                        )
                    }
                    speciesFilter?.let { species ->
                        FilterChip(
                            onClick = { viewModel.updateSpeciesFilter(null) },
                            label = { Text("Вид: $species") },
                            selected = true,
                            modifier = Modifier.height(42.dp)
                        )
                    }
                }
                
                // Вторая строка с фильтром пола
                genderFilter?.let { gender ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilterChip(
                            onClick = { viewModel.updateGenderFilter(null) },
                            label = { Text("Пол: $gender") },
                            selected = true,
                            modifier = Modifier.height(42.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Кнопка "Очистить все" на отдельной строке
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = viewModel::clearFilters) {
                        Text("Очистить все")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Characters Grid
            when {
                characters.loadState.refresh is androidx.paging.LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                characters.loadState.refresh is androidx.paging.LoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Ошибка загрузки данных")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { characters.retry() }) {
                                Text("Повторить")
                            }
                        }
                    }
                }
                else -> {
                    // Проверяем, что загрузка завершена и нет результатов
                    val isLoading = characters.loadState.refresh is androidx.paging.LoadState.Loading
                    val hasError = characters.loadState.refresh is androidx.paging.LoadState.Error
                    val isEmpty = characters.itemCount == 0
                    
                    if (isEmpty && !isLoading && !hasError) {
                        // Показываем сообщение когда нет результатов
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Таких персонажей нет",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                // Показываем разные сообщения в зависимости от контекста
                                val hasActiveFilters = statusFilter != null || speciesFilter != null || genderFilter != null
                                val hasSearchQuery = searchQuery.isNotBlank()
                                
                                val message = when {
                                    hasSearchQuery && hasActiveFilters -> 
                                        "По запросу \"$searchQuery\" с текущими фильтрами ничего не найдено"
                                    hasSearchQuery -> 
                                        "По запросу \"$searchQuery\" ничего не найдено"
                                    hasActiveFilters -> 
                                        "С текущими фильтрами ничего не найдено"
                                    else -> 
                                        "Персонажи не найдены"
                                }
                                
                                Text(
                                    text = message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Попробуйте изменить поисковый запрос или фильтры",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { 
                                        viewModel.clearFilters()
                                    }
                                ) {
                                    Text("Очистить все")
                                }
                            }
                        }
                    } else {
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
                            
                            // Индикатор загрузки для пагинации
                            if (characters.loadState.append is androidx.paging.LoadState.Loading) {
                                item(span = { GridItemSpan(2) }) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            }
            
            PullRefreshIndicator(
                refreshing = characters.loadState.refresh is androidx.paging.LoadState.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
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

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Поиск персонажей...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Поиск")
        },
        singleLine = true
    )
}
