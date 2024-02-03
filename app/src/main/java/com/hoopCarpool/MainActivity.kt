package com.hoopCarpool

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.navigation.AppNavigation
import com.hoopCarpool.util.Event
import com.hoopCarpool.util.EventBus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    //private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val lifecycle = LocalLifecycleOwner.current.lifecycle
            LaunchedEffect(key1 = lifecycle ){
                repeatOnLifecycle(state = Lifecycle.State.STARTED){
                    EventBus.events.collect(){event ->
                        if(event is Event.Toast){
                            Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            Surface(color = colorResource(id = R.color.primaryBackground)) {
                AppNavigation()
            }
        }


    }

}