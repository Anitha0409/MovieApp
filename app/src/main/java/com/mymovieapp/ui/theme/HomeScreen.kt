package com.mymovieapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mymovieapp.MovieViewModel

@Composable
fun HomeScreen(MovieviewModel: MovieViewModel, navController:NavController){

    val tabs = MovieviewModel.tabs
    val selectedIndex by MovieviewModel.selectedIndex.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.padding(16.dp)) {

           Box(modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp)) {
                IconButton(onClick = { navController.navigate("SearchScreen") }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription ="SearchButton", modifier = Modifier.align(
                        Alignment.CenterStart))
                }
                Text(text = "Movies", style = TextStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 26.sp), modifier = Modifier.align(Alignment.Center))
            }

            TabRow(selectedTabIndex = selectedIndex) {
                tabs.forEachIndexed{index,tab->
                    Tab(
                        selected = selectedIndex==index,
                        onClick = { MovieviewModel.onTabSelect(index)},
                        text = { Text(text = tab)}
                    )
                }
            }

            DisplayMovieList(movies = MovieviewModel.getFilteredMoviesByTabSelection(), navController = navController)

        }
        FloatingActionButton(
            onClick = { navController.navigate("FavListScreen") },
            modifier = Modifier.align(Alignment.BottomEnd),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription ="Fav list" )

        }
    }



}