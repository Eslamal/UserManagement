package com.example.madartask

import MainViewModel
import SettingsManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.madartask.data.AppDatabase
import com.example.madartask.ui.theme.MadarTaskTheme
import com.example.madartask.view.DisplayScreen
import com.example.madartask.view.InputScreen
import com.example.madartask.view.UserDetailsScreen

class MainActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database").build()
        val settingsManager = SettingsManager(applicationContext)
        val viewModel: MainViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(db.userDao(), settingsManager) as T
                }
            }
        }
      setContent {
            val currentLanguage by viewModel.currentLanguage.collectAsState()
            LaunchedEffect(currentLanguage) {
                LocaleHelper.setLocale(currentLanguage)
            }

        MadarTaskTheme {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "input") {
                composable("input") { InputScreen(viewModel, navController) }
                composable("display") { DisplayScreen(viewModel,navController) }
                composable(
                    "userDetails/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                    UserDetailsScreen(viewModel = viewModel, userId = userId, navController = navController)
                }
            }
        }
        }
    }
}