package com.mymovieapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mymovieapp.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen( movieViewModel:MovieViewModel, navController:NavController){

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="back button" )
                }
            },
                title = {
                        TextField(value = query, onValueChange = {query=it}, modifier = Modifier.fillMaxWidth(),
                                  singleLine = true, placeholder = {Text(text = "Search movies by title, genre")})

                },
                actions = {movieViewModel.searchMovie(query)}
                )
        }
        
    ) {innerPadding->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            if(query.isNotEmpty()) {
                DisplayMovieList(
                    movies = movieViewModel.searchMovie(query),
                    navController = navController
                )
            }else{
                Text(text = "Search by Movie Title or Genre")
            }
            if(movieViewModel.searchMovie(query).isEmpty()){
                Text(text = "Movie not found, check your spelling and try again")
            }

        }
        
    }

}
