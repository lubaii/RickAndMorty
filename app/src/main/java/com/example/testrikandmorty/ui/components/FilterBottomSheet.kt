package com.example.testrikandmorty.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit,
    onApplyFilters: (status: String?, species: String?, gender: String?) -> Unit,
    currentStatus: String?,
    currentSpecies: String?,
    currentGender: String?,
    modifier: Modifier = Modifier
) {
    var selectedStatus by remember { mutableStateOf(currentStatus) }
    var selectedSpecies by remember { mutableStateOf(currentSpecies) }
    var selectedGender by remember { mutableStateOf(currentGender) }
    
    val statusOptions = listOf("alive", "dead", "unknown")
    val speciesOptions = listOf("human", "alien", "humanoid", "poopybutthole", "mythological", "unknown", "animal", "disease", "robot", "cronenberg", "planet")
    val genderOptions = listOf("female", "male", "genderless", "unknown")
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Фильтры",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Status Filter
            Text(
                text = "Статус",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    onClick = { selectedStatus = null },
                    label = { Text("Все") },
                    selected = selectedStatus == null,
                    modifier = Modifier.height(40.dp)
                )
                statusOptions.forEach { status ->
                    FilterChip(
                        onClick = { selectedStatus = if (selectedStatus == status) null else status },
                        label = { Text(status.replaceFirstChar { it.uppercase() }) },
                        selected = selectedStatus == status,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Species Filter
            Text(
                text = "Вид",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Первая строка видов (максимум 4 кнопки)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    onClick = { selectedSpecies = null },
                    label = { Text("Все") },
                    selected = selectedSpecies == null,
                    modifier = Modifier.height(40.dp)
                )
                speciesOptions.take(3).forEach { species ->
                    FilterChip(
                        onClick = { selectedSpecies = if (selectedSpecies == species) null else species },
                        label = { Text(species.replaceFirstChar { it.uppercase() }) },
                        selected = selectedSpecies == species,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Вторая строка видов (3 кнопки)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                speciesOptions.drop(3).take(3).forEach { species ->
                    FilterChip(
                        onClick = { selectedSpecies = if (selectedSpecies == species) null else species },
                        label = { Text(species.replaceFirstChar { it.uppercase() }) },
                        selected = selectedSpecies == species,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Третья строка видов (Animal на новой строке)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                speciesOptions.drop(6).take(1).forEach { species ->
                    FilterChip(
                        onClick = { selectedSpecies = if (selectedSpecies == species) null else species },
                        label = { Text(species.replaceFirstChar { it.uppercase() }) },
                        selected = selectedSpecies == species,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Четвертая строка видов (оставшиеся кнопки)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                speciesOptions.drop(7).forEach { species ->
                    FilterChip(
                        onClick = { selectedSpecies = if (selectedSpecies == species) null else species },
                        label = { Text(species.replaceFirstChar { it.uppercase() }) },
                        selected = selectedSpecies == species,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Gender Filter
            Text(
                text = "Пол",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Первая строка полов
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    onClick = { selectedGender = null },
                    label = { Text("Все") },
                    selected = selectedGender == null,
                    modifier = Modifier.height(40.dp)
                )
                genderOptions.take(3).forEach { gender ->
                    FilterChip(
                        onClick = { selectedGender = if (selectedGender == gender) null else gender },
                        label = { Text(gender.replaceFirstChar { it.uppercase() }) },
                        selected = selectedGender == gender,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Вторая строка полов (unknown на новой строке)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                genderOptions.drop(3).forEach { gender ->
                    FilterChip(
                        onClick = { selectedGender = if (selectedGender == gender) null else gender },
                        label = { Text(gender.replaceFirstChar { it.uppercase() }) },
                        selected = selectedGender == gender,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedStatus = null
                        selectedSpecies = null
                        selectedGender = null
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Очистить")
                }
                
                Button(
                    onClick = {
                        onApplyFilters(selectedStatus, selectedSpecies, selectedGender)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Применить")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


