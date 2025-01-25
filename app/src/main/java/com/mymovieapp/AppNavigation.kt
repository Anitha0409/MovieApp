package com.mymovieapp

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
   NavHost(navController = navController, startDestination = "HomeScreen") {
       composable("HomeScreen"){
           val movieViewModel:MovieViewModel = viewModel()
           HomeScreen(movieViewModel,navController = navController)
       }
       composable("SearchScreen"){
           val backStackEntryVM = remember {navController.getBackStackEntry("HomeScreen")}
           val movieViewModel:MovieViewModel = viewModel(backStackEntryVM)
           SearchScreen( movieViewModel,navController =navController )
       }
       composable(
           route = "MovieDetailsScreen/{id}",
           arguments = listOf(navArgument("id"){type = NavType.StringType})
       ){backstackEntry->
           val backStackEntryVM = remember {navController.getBackStackEntry("HomeScreen")}
           val movieViewModel:MovieViewModel = viewModel(backStackEntryVM)
           val movieId = backstackEntry.arguments?.getString("id")
           MovieDetailsScreen(movieId = movieId, movieViewModel =movieViewModel , navController =navController )
       }
       composable(
           route = "FavListScreen"
       ){
           val backStackEntryVM = remember {navController.getBackStackEntry("HomeScreen")}
           val movieViewModel:MovieViewModel = viewModel(backStackEntryVM)
           FavListScreen(movieViewModel = movieViewModel, navController =navController )
       }
   }

}