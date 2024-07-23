package com.cj.amuse.frameworks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cj.amuse.frameworks.models.BottomNavGraph
import com.cj.amuse.frameworks.models.BottomNavItem
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navItems = listOf(
        BottomNavItem.HOME,
        BottomNavItem.BOOK,
        BottomNavItem.MOVIE,
        BottomNavItem.CULTURE,
        BottomNavItem.MORE
    )

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    AMUSETheme {
        Scaffold(topBar = {
            LargeTopAppBar(title = { Text(text = currentRoute ?: "", style = Typography.titleLarge) })
        }, bottomBar = {
            NavigationBar {
                navItems.forEach {
                    NavigationBarItem(selected = currentRoute == it.getString(), onClick = {
                        navController.navigate(it.getString()) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }, icon = {
                        it.getIcon()?.let { it1 ->
                                Icon(
                                    imageVector = it1, contentDescription = null
                                )
                            }
                    }, label = {
                        Text(text = it.getString())
                    }, alwaysShowLabel = false)
                }
            }
        }) {
            Box(modifier = Modifier.padding(it)) {
                BottomNavGraph(navHostController = navController)
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView()
}
