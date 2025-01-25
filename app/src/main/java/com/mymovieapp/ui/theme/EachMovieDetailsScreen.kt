package com.mymovieapp.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mymovieapp.Movie
import com.mymovieapp.MovieStatus
import com.mymovieapp.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachMovieDetailsScreen(movie:Movie,movieViewModel: MovieViewModel, navController: NavController){

    var isFav by remember { mutableStateOf( movieViewModel.isFav(movie.id))}

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                        IconButton(onClick = { navController.popBackStack()}) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back button" )
                        }
            },
                title = { Text(text = "Detail Screen") })
        }

    ) {innerPadding->
        Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.Center){
            MovieCol(
                id = movie.id,
                title =movie.title ,
                rating = movie.rating,
                genre = movie.genre,
                releaseDate = movie.releaseDate,
                description =movie.description ,
                isFavourite = isFav,
                imageId = movie.imageId,
                onClick = {
                    if(isFav){
                        movieViewModel.removeFromFavList(movie.id)
                    }else{
                        movieViewModel.addToFavList(movie.id)
                    }
                    isFav =!isFav
                },
                status = movie.status,
                buttonOnClick = {action->
                    when(action){
                        "Add to Watching List"->{movieViewModel.getAddedToWatchingList(movie.id)}
                        "Add to Wanna Watch List"->{movieViewModel.getAddedToWannaWatchList(movie.id)}
                        "Add to Watched List"->{movieViewModel.getAddedToWatchedList(movie.id)}
                    }

                }
            )
        }

    }
    }


@Composable
fun MovieCol(id:Int,
             title:String,
             rating:Float,
             genre:String,
             releaseDate:String, description:String, isFavourite:Boolean, imageId:Int, onClick:()->Unit,
             buttonOnClick:(String)->Unit,
             status:MovieStatus) {

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
        )

        Row {
            Text(text = "Rating")
            RatingBar(rating)
        }
        Text(
            text = "Genre $genre",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 15.sp
        )
        Text(
            text = "Release Date is $releaseDate",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 15.sp
        )
        Text(
            text = description,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )
        FloatingActionButton(onClick = { onClick() }) {
            if (isFavourite) Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Fav button"
            ) else Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Fav button"
            )
        }
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Movie Image",
            modifier = Modifier
                .padding(16.dp)
                .height(180.dp)
                .fillMaxWidth(), contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            when (status) {
                MovieStatus.DEFAULT -> {
                    ButtonColumn(buttonOnClick = buttonOnClick, buttonLabels = listOf("Watched","Watching","Wanna Watch") )
                }
                MovieStatus.WATCHED -> {
                    ButtonColumn(buttonOnClick = buttonOnClick, buttonLabels = listOf("Watching","Wanna Watch"))
                }
                MovieStatus.WATCHING -> {
                    ButtonColumn(buttonOnClick = buttonOnClick, buttonLabels = listOf("Watched","Wanna Watch") )
                }
                MovieStatus.WANNA_WATCH -> {
                    ButtonColumn(buttonOnClick = buttonOnClick, buttonLabels = listOf("Watched","Watching") )

                }
            }

        }
    }
}
@Composable
fun RatingBar(rating:Float){
    Row {
        for(i in 1..rating.toInt()+1){
            Icon(imageVector = when{
                                   i<=rating->Icons.Filled.Star
                                   i-1<rating && i>rating-> Icons.Filled.StarHalf
                else-> Icons.Outlined.Star
            },
                contentDescription = "Star",
                tint = Color(0xFFFFD700))

        }
    }

}

@Composable
fun ButtonColumn(buttonOnClick: (String) -> Unit,
                 buttonLabels:List<String>){
    Column (modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly){
        val context = LocalContext.current
        buttonLabels.forEach{button->
            Button(modifier = Modifier.fillMaxWidth(),
                  onClick ={
            Toast.makeText(context,"Movie added to $button List",Toast.LENGTH_SHORT).show()
            buttonOnClick("Add to $button List") }) {

                Text(text = "Add to $button List", style = TextStyle(fontSize = 16.sp))
            }
        }

    }

}

