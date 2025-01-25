package com.mymovieapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavListScreen(movieViewModel: MovieViewModel, navController: NavController){

    val favListMovies = movieViewModel.getFavMoviesList()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Your favorite list of movies") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back button")
                    }
                })
        }

    ) {innerPadding->
        Box(modifier = Modifier.padding(innerPadding)){
            DisplayMovieList(movies = favListMovies, navController = navController)
        }

    }

}