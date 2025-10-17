package com.example.testrikandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testrikandmorty.ui.screen.CharactersScreen
import com.example.testrikandmorty.ui.screen.CharacterDetailScreen

@Composable
fun RickAndMortyNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "characters"
    ) {
        composable("characters") {
            CharactersScreen(
                onCharacterClick = { characterId ->
                    navController.navigate("character_detail/$characterId")
                }
            )
        }
        
        composable("character_detail/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull() ?: 0
            CharacterDetailScreen(
                characterId = characterId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}



