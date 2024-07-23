package com.cj.amuse.frameworks.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cj.amuse.book.ui.BookView
import com.cj.amuse.culture.ui.CultureView
import com.cj.amuse.home.ui.HomeView
import com.cj.amuse.more.ui.MoreView
import com.cj.amuse.movie.ui.MovieView

@Composable
fun BottomNavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = BottomNavItem.HOME.getString()) {
        composable(BottomNavItem.HOME.getString()){
            HomeView()
        }

        composable(BottomNavItem.BOOK.getString()){
            BookView()
        }

        composable(BottomNavItem.MOVIE.getString()){
            MovieView()
        }

        composable(BottomNavItem.CULTURE.getString()){
            CultureView()
        }

        composable(BottomNavItem.MORE.getString()){
            MoreView()
        }
    }
}