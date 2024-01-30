package com.hoopCarpool.movies.usescases.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.usescases.common.Components.movieDetail.MovieDetailViewModel
import com.hoopCarpool.movies.usescases.favoritesMovies.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(navController: NavController, movieId: String, homeViewModel: HomeViewModel, favoriteViewModel: FavoriteViewModel){

    var movieDetailViewModel: MovieDetailViewModel = viewModel()

    val movieItem = remember {
        homeViewModel.getMoviesInmutable().first {
            it.id == movieId
        }
    }
    val favoriteStatus = movieDetailViewModel.favoriteStatus.observeAsState(initial = movieItem.favorite).value

    val painter = rememberImagePainter(
        data = movieItem.imageUrl,
        builder = {
            crossfade(true)
            error(
                R.drawable.ic_no_image
            )
        }
    )
    val painterState = painter.state


    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent
            ),
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }) {
                    Icon(imageVector = Icons.Default.ArrowBack, "backButton", tint = Color.White)
                }
            }
        )
        
        Column {
            
            Image(
                painter = painter,
                contentDescription = "Image item detail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row (
                Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start

            ){
                Image(
                    painter = painter,
                    contentDescription = "Image 2 item detail",
                    modifier = Modifier
                        .size(200.dp, 200.dp)
                )
                Text(
                    text = movieItem.subtitle,
                    color = colorResource(id = R.color.primaryTextColor),
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
            }
            
        }
        FloatingActionButton(
            onClick = {
                if (!favoriteStatus){
                    favoriteViewModel.addToFavorites(movieItem)
                    movieItem.favorite = true
                    movieDetailViewModel.favoriteStatus.postValue(true)
                    homeViewModel.updateMovie(movieItem)
                }else{
                    movieItem.favorite = false
                    movieDetailViewModel.favoriteStatus.postValue(false)
                    favoriteViewModel.removeFromFavorites(movieItem)
                    homeViewModel.updateMovie(movieItem)
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = if (!favoriteStatus) {
                    colorResource(id = R.color.primaryButtonColor)
                } else {
                    Color.Red
                }
        ) {
            Row (
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(10.dp)
            ){
                if(!favoriteStatus){
                    Icon(
                        imageVector = Icons.Default.Add,
                        "Add to favorites",
                        tint = colorResource(id = R.color.secondaryTextColor)
                    )
                    Text(
                        text = "Add to favorites",
                        color = colorResource(id = R.color.secondaryTextColor)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Delete,
                        "remove from favorites",
                        tint = colorResource(id = R.color.secondaryTextColor)
                    )
                    Text(
                        text = "remove from favorites",
                        color = colorResource(id = R.color.secondaryTextColor)
                    )
                }
            }
        }

    }
}

/*@Composable
@Preview
fun preview(){
    val navController = rememberNavController()
    MovieDetailScreen(navController = navController, movieId = "1")
}*/
