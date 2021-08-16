package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavigationContainerPage()
                }
            }
        }
    }
}

@Composable
fun NavigationContainerPage() {
    val navItems = listOf(
        Screen.FirstScreen,
        Screen.SecondScreen,
        Screen.ThirdScreen,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDestination by navBackStackEntry?.destination
                navItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(text = screen.label) },
                        onClick = {
                            navController.navigate(screen.route)
                        },
                        selected = true,
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.FirstScreen.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.FirstScreen.route) { ContentPage("First screen") }
            composable(Screen.SecondScreen.route) { ContentPage("Second screen") }
            composable(Screen.ThirdScreen.route) { ContentPage("Third screen") }
        }

    }
}

@Composable
fun SampleBottomAppBar(navController: NavController = rememberNavController()) {
    val tabs = listOf(
        Screen.FirstScreen,
        Screen.SecondScreen,
        Screen.ThirdScreen,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation {
        tabs.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(text = tab.label) },
                selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                onClick = {
                    navController.navigate(tab.route)
                },
            )
        }
    }
}

@Composable
fun ContentPage(message: String) {
    Text("message: $message")
}

sealed class Screen(val route: String, val label: String, val message: String) {
    object FirstScreen : Screen(route = "first", label = "First", message = "First screen")
    object SecondScreen : Screen(route = "second", label = "Second", message = "Second screen")
    object ThirdScreen : Screen(route = "third", label = "Third", message = "Third screen")
}

@Preview
@Composable
fun SampleBottomAppBarPreview() {
    MyApplicationTheme {
        SampleBottomAppBar()
    }
}
