package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class RallyNavHostTest {
    @get:Rule
    val composableTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @Before
    fun setup() {
        composableTestRule.setContent {
            navController = rememberNavController()
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost() {
        composableTestRule.onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_viaUI() {
        composableTestRule
            .onNodeWithContentDescription("All Accounts")
            .performClick()

        composableTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToBills_viaUI() {
        composableTestRule
            .onNodeWithContentDescription("All Bills").apply {
                performScrollTo()
                performClick()
            }

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "Bills")
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_callingNavigate() {
        runBlocking {
            withContext(Main) {
                navController.navigate(RallyScreen.Accounts.name)
            }
        }
        composableTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }
}
