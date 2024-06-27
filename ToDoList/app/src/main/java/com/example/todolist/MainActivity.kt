package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.Content.*
import com.example.todolist.Main.Main_Screen
import com.example.todolist.Sub.TaskViewModel
import com.example.todolist.ui.theme.TodoListTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val navController = rememberNavController()
            TodoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val taskViewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.MainScreen) {
        composable(Routes.MainScreen) {
            Main_Screen(navController = navController)
        }
        composable(Routes.TodayTasks) {
            Today_Tasks(navController, taskViewModel)
        }
        composable(Routes.HomeTasks) {
            Home_Tasks(navController, taskViewModel)
        }
        composable(Routes.ImportantTasks) {
            Important_Tasks(navController, taskViewModel)
        }
        composable(Routes.MyTask) {
            My_Task(navController, taskViewModel)
        }
        composable(
            route = "${Routes.WriteScreen}/{from}",
            arguments = listOf(navArgument("from") { type = NavType.StringType })
        ) { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from") ?: "MyTask"
            Write(navController, taskViewModel, from)
        }
    }
}

object Routes {
    const val MainScreen = "Main_Screen"
    const val TodayTasks = "Today_Tasks"
    const val HomeTasks = "Home_Tasks"
    const val ImportantTasks = "Important_Tasks"
    const val WriteScreen = "WriteScreen"
    const val MyTask = "My_Task"

    fun getWriteScreenRoute(from: String) = "WriteScreen/$from"
}
