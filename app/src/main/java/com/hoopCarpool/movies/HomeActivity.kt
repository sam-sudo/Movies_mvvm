package com.hoopCarpool.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.navigation.AppNavigation

class HomeActivity: AppCompatActivity() {

    //private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Surface(color = colorResource(id = R.color.primaryBackground)) {
                AppNavigation()
            }
        }


    }

}




@Preview(showBackground = true)
@Composable
fun ListPreview() {

    Surface(color = colorResource(id = R.color.primaryBackground)) {
        AppNavigation()
    }
}
