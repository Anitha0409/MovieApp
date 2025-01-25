package com.mymovieapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MovieViewModel:ViewModel() {

    private var _moviesList = MutableStateFlow(sampleMovies)
    var moviesList: StateFlow<List<Movie>> = _moviesList

    private var _tabs = listOf("Watched", "Watching","Wanna_Watch")
    var tabs: List<String> = _tabs

    private var _selectedIndex = MutableStateFlow(0)
    var selectedIndex:StateFlow<Int> =_selectedIndex

    fun searchMovie(query: String): List<Movie> {
        val searchResult = _moviesList.value.filter { movie ->
            (movie.title.contains(query, ignoreCase = true) || movie.genre.contains(
                query,
                ignoreCase = true
            ))

        }
        return searchResult

    }

    fun getDefaultMoviesList(): List<Movie> {
        return _moviesList.value.filter { it.status == MovieStatus.DEFAULT }

    }

    fun getMovieDetail(id: Int): Movie? {
        return _moviesList.value.find { movie ->
            movie.id == id
        }
    }

    fun isFav(id: Int): Boolean {
        return _moviesList.value.any { movie ->
            movie.id == id && movie.isFavorite
        }
    }

    fun addToFavList(id: Int) {
        _moviesList.value = _moviesList.value.map { movie ->
            if (movie.id == id && !movie.isFavorite) {
                movie.copy(isFavorite = true)
            } else {
                movie
            }
        }
    }


    fun removeFromFavList(id: Int) {
        _moviesList.value = _moviesList.value.map { movie ->
            if (movie.id == id && movie.isFavorite) {
                movie.copy(isFavorite = false)
            } else {
                movie
            }
        }
    }


    fun getFavMoviesList(): List<Movie> {
        return _moviesList.value.filter { movie ->
            movie.isFavorite
        }

    }

    fun getWatchedMovies(): List<Movie> {
        return _moviesList.value.filter {movie->
            movie.status==MovieStatus.WATCHED
        }

    }

    fun getWannaWatchMovies(): List<Movie> {
        return _moviesList.value.filter {movie->
            movie.status==MovieStatus.WANNA_WATCH
        }

    }

    fun getWatchingMovies(): List<Movie> {
        return _moviesList.value.filter {movie->
            movie.status==MovieStatus.WATCHING
        }
    }

    fun onTabSelect(index:Int){
        _selectedIndex.value = index
    }


    fun getFilteredMoviesByTabSelection():List<Movie>{
        val filteredMovies = when(_selectedIndex.value){
            0->getWatchedMovies()
            1->getWatchingMovies()
            2->getWannaWatchMovies()
            else -> {
                getDefaultMoviesList()
            }
        }
        return filteredMovies
    }

    fun getAddedToWatchedList(id:Int){
            _moviesList.value= _moviesList.value.map {movie ->
                if(movie.id==id){
                    movie.copy(status = MovieStatus.WATCHED)
                }else{
                    movie
                }
            }
        }

    fun getAddedToWatchingList(id:Int){
        _moviesList.value = _moviesList.value.map {movie->
            if(movie.id==id){
                movie.copy(status = MovieStatus.WATCHING)
            }else{
                movie
            }
        }

    }

    fun getAddedToWannaWatchList(id:Int){
        _moviesList.value = _moviesList.value.map {movie->
            if(movie.id==id){
                movie.copy(status = MovieStatus.WANNA_WATCH)
            }else{
                movie
            }

        }

    }
}


data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genre: String,
    val description: String,
    val rating: Float,
    var isFavorite: Boolean = false, // Default to false
    var status: MovieStatus = MovieStatus.DEFAULT,
    val imageId:Int
)

enum class MovieStatus {
    DEFAULT,    // Not categorized yet
    WATCHED,    // In the "Watched" tab
    WATCHING,   // In the "Watching" tab
    WANNA_WATCH // In the "Wanna Watch" tab
}

val sampleMovies = listOf(
    // Default Movies (8 movies)
    Movie(1, "The Shawshank Redemption", "1994-09-22", "Drama", "Two imprisoned men bond over a number of years.", 4.3f, false, MovieStatus.DEFAULT, R.drawable.shawshankredemption),
    Movie(2, "The Godfather", "1972-03-24", "Crime", "The aging patriarch of an organized crime dynasty transfers control to his reluctant son.", 2.2f, false, MovieStatus.DEFAULT, R.drawable.godfather),
    Movie(16, "Jurassic Park", "1993-06-11", "Adventure", "During a preview tour, a theme park suffers a major power breakdown.", 4.1f, false, MovieStatus.DEFAULT, R.drawable.jurrasicpark),
    Movie(17, "Avatar", "2009-12-18", "Sci-Fi", "A paraplegic Marine is dispatched to Pandora on a mission but becomes torn between orders and protecting its people.", 3.8f, false, MovieStatus.DEFAULT, R.drawable.avatar),
    Movie(18, "Coco", "2017-11-22", "Animation", "Aspiring musician Miguel enters the Land of the Dead to find his ancestor.", 3.4f, true, MovieStatus.DEFAULT, R.drawable.coco),
    Movie(19, "Shrek", "2001-05-18", "Animation", "A mean lord exiles fairy tale creatures to the swamp of a grumpy ogre.", 4.9f, false, MovieStatus.DEFAULT, R.drawable.shrek),
    Movie(20, "The Incredibles", "2004-11-05", "Animation", "A family of undercover superheroes try to live a quiet life.", 5.0f, true, MovieStatus.DEFAULT, R.drawable.incredibles),
    Movie(21, "Toy Story", "1995-11-22", "Animation", "A cowboy doll feels threatened by a new spaceman action figure.", 4.3f, false, MovieStatus.DEFAULT, R.drawable.toystory),

    // Watched Movies (8 movies)
    Movie(3, "The Dark Knight", "2008-07-18", "Action", "When the menace known as the Joker emerges, Batman must accept one of the greatest psychological tests.", 4.0f, true, MovieStatus.WATCHED, R.drawable.darkknight),
    Movie(4, "Pulp Fiction", "1994-10-14", "Crime", "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine.", 3.9f, false, MovieStatus.WATCHED, R.drawable.pulpfiction),
    Movie(5, "The Lord of the Rings: The Return of the King", "2003-12-17", "Fantasy", "Gandalf and Aragorn lead the World of Men against Sauron's army.", 2.9f, true, MovieStatus.WATCHED, R.drawable.lordoftherings),
    Movie(6, "Forrest Gump", "1994-07-06", "Drama", "The presidencies of Kennedy and Johnson, the Vietnam War, and more through the perspective of an Alabama man.", 1.8f, false, MovieStatus.WATCHED, R.drawable.forrestgump),
    Movie(22, "Titanic", "1997-12-19", "Drama", "A seventeen-year-old aristocrat falls in love with a kind but poor artist.", 2.9f, false, MovieStatus.WATCHED, R.drawable.titanic),
    Movie(23, "Gladiator", "2000-05-05", "Action", "A former Roman General sets out to exact vengeance against the corrupt emperor.", 4.5f, true, MovieStatus.WATCHED, R.drawable.gladiator),
    Movie(24, "Saving Private Ryan", "1998-07-24", "Drama", "A group of U.S. soldiers go behind enemy lines to retrieve a paratrooper.", 4.6f, false, MovieStatus.WATCHED, R.drawable.savingprivateryan),
    Movie(25, "Schindler's List", "1993-11-30", "Drama", "In German-occupied Poland, a businessman becomes concerned for his Jewish workforce.", 4.0f, true, MovieStatus.WATCHED, R.drawable.schindlerslist),

    // Watching Movies (8 movies)
    Movie(7, "Inception", "2010-07-16", "Sci-Fi", "A thief who steals corporate secrets through dream-sharing technology is given a chance to erase his past.", 2.8f, true, MovieStatus.WATCHING, R.drawable.inception),
    Movie(8, "Fight Club", "1999-10-15", "Drama", "An insomniac office worker and a soap salesman form an underground fight club.", 3.8f, false, MovieStatus.WATCHING, R.drawable.thefightclub),
    Movie(9, "The Matrix", "1999-03-31", "Sci-Fi", "A computer hacker learns about the true nature of reality and his role in the war against its controllers.", 4.7f, false, MovieStatus.WATCHING, R.drawable.matrix),
    Movie(10, "Goodfellas", "1990-09-19", "Crime", "The story of Henry Hill and his life in the mob.", 3.7f, true, MovieStatus.WATCHING, R.drawable.goodfellas),
    Movie(26, "Dune", "2021-10-22", "Sci-Fi", "A noble family becomes embroiled in a war over the desert planet Arrakis.", 5.0f, true, MovieStatus.WATCHING, R.drawable.dune),
    Movie(27, "The Mandalorian", "2019-11-12", "Sci-Fi", "A bounty hunter travels to deliver a unique package.", 3.7f, false, MovieStatus.WATCHING, R.drawable.mandalorian),
    Movie(28, "Breaking Bad", "2008-01-20", "Crime", "A high school chemistry teacher turned methamphetamine producer.", 4.5f, false, MovieStatus.WATCHING, R.drawable.breakingbad),
    Movie(29, "Stranger Things", "2016-07-15", "Sci-Fi", "A group of kids uncovering supernatural mysteries in their town.", 5.0f, false, MovieStatus.WATCHING, R.drawable.strangerthings),

    // Wanna Watch Movies (8 movies)
    Movie(11, "The Silence of the Lambs", "1991-02-14", "Thriller", "A young FBI cadet seeks the help of a manipulative cannibal killer.", 2.6f, false, MovieStatus.WANNA_WATCH, R.drawable.silenceofthelambs),
    Movie(12, "Se7en", "1995-09-22", "Thriller", "Two detectives hunt a serial killer who uses the seven deadly sins as his motives.", 3.6f, true, MovieStatus.WANNA_WATCH, R.drawable.se7en),
    Movie(13, "The Green Mile", "1999-12-10", "Drama", "The lives of guards on Death Row are affected by one of their charges, a black man accused of child murder.", 4.6f, false, MovieStatus.WANNA_WATCH, R.drawable.thegreenmile),
    Movie(14, "Interstellar", "2014-11-07", "Sci-Fi", "A team of explorers travel through a wormhole in space in an attempt to save humanity.", 3.6f, true, MovieStatus.WANNA_WATCH, R.drawable.interstellar),
    Movie(15, "The Prestige", "2006-10-20", "Drama", "Two stage magicians engage in a bitter rivalry, making sacrifices for fame.", 1.5f, false, MovieStatus.WANNA_WATCH, R.drawable.theprestige),
    Movie(30, "The Grand Budapest Hotel", "2014-03-28", "Comedy", "A concierge and his lobby boy get caught up in a heist and murder.", 2.1f, false, MovieStatus.WANNA_WATCH, R.drawable.thegrandbudapest),
    Movie(31, "Whiplash", "2014-10-10", "Drama", "A promising drummer enrolls in a cutthroat music conservatory.", 5.0f, true, MovieStatus.WANNA_WATCH, R.drawable.whiplash),
    Movie(32, "The Social Network", "2010-10-01", "Biography", "The story of the creation of Facebook.", 2.8f, true, MovieStatus.WANNA_WATCH, R.drawable.thesocialnetwork)
)
