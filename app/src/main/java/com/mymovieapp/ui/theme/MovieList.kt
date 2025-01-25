package com.mymovieapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mymovieapp.Movie
import com.mymovieapp.MovieViewModel

@Composable
fun DisplayMovieList(movies:List<Movie>, navController: NavController){

    LazyColumn(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        items(movies){movie->
            MovieCol(id = movie.id,
                title =movie.title ,
                rating =movie.rating ,
                imageId =movie.imageId,
                onClick = {navController.navigate("MovieDetailsScreen/${movie.id}")})

        }
    }
}
@Composable
fun MovieCol(id:Int, title:String, rating:Float, imageId:Int, onClick:()->Unit) {

    Column(modifier = Modifier.padding(16.dp).clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
            )
            Text(text = "Rating $rating", style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 15.sp)
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Movie Image",
                modifier = Modifier
                    .padding(16.dp)
                    .height(300.dp)
                    .fillMaxWidth(), contentScale = ContentScale.Crop
            )
        }
    }

