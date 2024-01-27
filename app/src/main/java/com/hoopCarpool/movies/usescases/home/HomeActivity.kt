package com.hoopCarpool.movies.usescases.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class HomeActivity: AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            homeScreen(viewModel)
        }


    }

}



@Preview(showBackground = true)
@Composable
fun ListPreview() {

    homeScreen(viewModel = HomeViewModel())
}