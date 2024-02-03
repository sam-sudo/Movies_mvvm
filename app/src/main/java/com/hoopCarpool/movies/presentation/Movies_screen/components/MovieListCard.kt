package com.hoopCarpool.movies.presentation.Movies_screen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.navigation.AppScreens

@Composable
fun MovieListCard(movie : Movie, navController: NavController) {

    val painter = rememberImagePainter(
        data = movie.imageUrl,
        builder = {
            crossfade(true)
            allowHardware(false)
            error(
                R.drawable.ic_no_image
            )

        }
    )
    val painterState = painter.state

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
            .padding(16.dp)
            .clickable {
                Log.w("TAG", "ListItemCard detail ",)
                navController.navigate(route = AppScreens.MovieDetailScreen.route + "/" + movie.id)
            }

    ) {

        if (painterState is ImagePainter.State.Loading) {

        } else {
            Row (
                modifier = Modifier
                    .background(colorResource(id = R.color.primaryBackground))

            ){
                CoilImage(
                    movie = movie,
                    painter = painter,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(height = 200.dp, width = 110.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ){
                    Text(
                        text = movie.title,
                        color = colorResource(R.color.primaryTextColor),
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription ="Count stars",
                            tint = colorResource(id = R.color.starsColor),
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = String.format("%.1f", movie.stars),

                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(R.color.primaryTextColor)
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        var year = movie.date.split("-")[0]
                        Text(
                            text = year ,
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(R.color.primaryTextColor)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = String.format("%.1f", movie.duration),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(R.color.primaryTextColor)
                        )
                    }
                }
            }


        }

    }


}

@Composable
fun CoilImage(
    movie: Movie,
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,

    ) {
    Box(modifier = modifier) {
        // Utilizando Coil para cargar la imagen
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )

        // Agregar un icono en la esquina superior izquierda
        Box (
            modifier =
            Modifier
                .size(40.dp, 50.dp)
                .background(
                    if (movie.favorite) colorResource(id = R.color.starsColor) else Color.Gray,
                    shape = if (movie.favorite) InvertedTriangleShape() else RectangleShape
                )
        ){
            Image(
                imageVector = if(movie.favorite)  Icons.Default.Check else Icons.Default.Add ,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
        }

    }

}



@Composable
fun InvertedTriangleShape() = GenericShape { size, _ ->
    val middleX = size.width / 2
    val quarterY = size.height / 4
    moveTo(middleX, quarterY * 3)
    lineTo(size.width, 0f)
    lineTo(0f, 0f)
    lineTo(0f , size.height)
    moveTo(middleX, quarterY * 3)
    lineTo(size.width  , 0f)
    lineTo(size.width  , size.height)
    close()
}