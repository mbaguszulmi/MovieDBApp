package com.mbaguszulmi.moviedbapp.helper

import com.mbaguszulmi.moviedbapp.model.database.entities.Genre
import com.mbaguszulmi.moviedbapp.model.network.Movie

object DummyHelper {
    fun getMovies(): ArrayList<Movie> {
        return ArrayList<Movie>().apply {
            add(Movie(578701, false, "/iDdpiUnCeXvBmrkBFpL6lKsZt33.jpg",
                ArrayList<Genre>().apply {
                    add(Genre(53, "Thriller"))
                    add(Genre(18, "Drama"))
                    add(Genre(28, "Action"))
                    add(Genre(9648, "Mystery"))
                }, "https://www.warnerbros.com/movies/those-who-wish-me-dead", "tt3215824",
                "en", "Those Who Wish Me Dead",
                "A young boy finds himself pursued by two assassins in the Montana " +
                        "wilderness with a survival expert determined to protecting him - and " +
                        "a forest fire threatening to consume them all.", 2835.542,
                "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg", "2021-05-05", 100,
                "Released", "Nature finds a way.", "Those Who Wish Me Dead",
                7.2, 264))

            add(Movie(460465, false, "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
                ArrayList<Genre>().apply {
                    add(Genre(28, "Action"))
                    add(Genre(14, "Fantasy"))
                    add(Genre(12, "Adventure"))
                }, "https://www.mortalkombatmovie.net", "tt0293429",
                "en", "Mortal Kombat",
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by " +
                        "Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with " +
                        "Earth's greatest champions as he prepares to stand against the enemies of " +
                        "Outworld in a high stakes battle for the universe.", 2757.774,
                "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg", "2021-04-07", 110,
                "Released", "Get over here.", "Mortal Kombat",
                7.6, 2641))
        }
    }
}
