package com.mymovieapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MovieDetailsScreen(movieId:String?, movieViewModel:MovieViewModel, navController: NavController){

    if(movieId!=null) {
        val details = movieViewModel.getMovieDetail(movieId.toInt())
        if (details != null) {
            EachMovieDetailsScreen(
                movieViewModel = movieViewModel,
                movie = details,
                navController = navController
            )
        }
    }else{
        Text(text = "Sorry something went wrong, please try again")
    }



}