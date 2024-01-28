package com.hoopCarpool.movies.providers.services

import com.hoopCarpool.movies.model.Movie

class MoviesProvider {

    //private val movieService = RetrofitIntance.getMoviesService

    suspend fun getMovies(): List<Movie>{
        var urlTemp = "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_FMjpg_UX1000_.jpg"

        return listOf(
            Movie("0","Item 1", "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas \"Letraset\", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum.\n" +
                    "\n", urlTemp, 8.5, "2021", "1h 30m", true),
            Movie("1","Item 2", "Subtitle 2", urlTemp, 7.8, "2022", "1h 45m", false),
            Movie("2","Item 3", "Subtitle 3", urlTemp, 9.0, "2023", "2h 10m", true),
            Movie("3","Item 4", "Subtitle 4", "", 8.2, "2024", "1h 55m", false),
            Movie("4","Item 5", "Subtitle 5", urlTemp, 8.9, "2025", "2h 5m", true),
            Movie("5","Item 6", "Subtitle 6", urlTemp, 7.5, "2026", "1h 40m", false),
            Movie("6","Item 7", "Subtitle 7", urlTemp, 9.3, "2027", "2h 20m", true),
            Movie("7","Item 8", "Subtitle 8", urlTemp, 7.0, "2028", "1h 50m", false),
            Movie("8","Item 9", "Subtitle 9", urlTemp, 8.7, "2029", "2h", true),
            Movie("9","Item 10", "Subtitle 10", urlTemp, 7.4, "2030", "1h 55m", false)
            // ... agregar más elementos según sea necesario
        )
    }
}