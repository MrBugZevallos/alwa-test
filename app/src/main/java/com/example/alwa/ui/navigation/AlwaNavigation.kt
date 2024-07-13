package com.example.alwa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alwa.ui.AppViewModel
import com.example.alwa.ui.components.Apps
import com.example.alwa.ui.components.RegisterNewApp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.alwa.data.model.AppMetric
import com.example.alwa.ui.components.AppDetail

@Composable
fun AlwaNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppRoute.APPS_ROUTE,
    modifier: Modifier,
    viewModel: AppViewModel
) {
    val actions = remember(navController) { AppActions(navController) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var appMetricSelected = remember { AppMetric() }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppRoute.APPS_ROUTE) { backStackEntry ->
            Apps(
                modifier = modifier,
                appUIState = uiState,
                addNewApp = {
                    actions.openNewApp(backStackEntry)
                },
                onDetail = {
                    appMetricSelected = it
                    actions.openAppDetail(backStackEntry)
                }
            )
        }

        composable(AppRoute.NEW_APP_ROUTE) { backStackEntry ->
            RegisterNewApp(
                modifier = modifier,
                upPress = {
                    actions.upPress(backStackEntry)
                },
                onAdd = {
                    viewModel.addApp(it)
                }
            )
        }

        composable(AppRoute.APP_DETAIL_ROUTE) { backStackEntry ->
            AppDetail(
                modifier = modifier,
                appMetric = appMetricSelected,
                upPress = {
                    actions.upPress(backStackEntry)
                },
                onDelete = {
                    viewModel.deleteApp(it)
                }
            )
        }
    }
}

class AppActions(navController: NavHostController) {
    val openAppDetail = { from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            navController.navigate(AppRoute.APP_DETAIL_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    val openNewApp = { from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            navController.navigate(AppRoute.NEW_APP_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    val upPress: (from: NavBackStackEntry) -> Unit = { from ->
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }
}

object AppRoute {
    const val NEW_APP_ROUTE = "newApp"
    const val APPS_ROUTE = "apps"
    const val APP_DETAIL_ROUTE = "appDetail"
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

