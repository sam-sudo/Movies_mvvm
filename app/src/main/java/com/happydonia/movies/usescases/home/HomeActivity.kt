package com.happydonia.movies.usescases.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import com.happydonia.movies.model.Movie

class HomeActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListScreen()
        }


    }

}



val sampleData = listOf(
    Movie("Item 1", "Subtitle 1", Icons.Default.Favorite),
    Movie("Item 2", "Subtitle 2", Icons.Default.ThumbUp),
    Movie("Item 3", "Subtitle 3", Icons.Default.Warning),
    Movie("Item 4", "Subtitle 4", Icons.Default.DateRange),
    Movie("Item 5", "Subtitle 4", Icons.Default.DateRange),
    Movie("Item 6", "Subtitle 4", Icons.Default.DateRange),
    Movie("Item 7", "Subtitle 4", Icons.Default.DateRange),
    Movie("Item 8", "Subtitle 4", Icons.Default.DateRange),
    Movie("Item 9", "Subtitle 4", Icons.Default.DateRange),
    // ... agregar más elementos según sea necesario
)

@Composable
fun ListItemCard(item: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = item.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = item.subtitle, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ItemList() {
    LazyColumn {
        items(sampleData) { item ->
            ListItemCard(item = item)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen() {
    Scaffold(
        modifier = Modifier.padding(10.dp),
    ) {
        ItemList()
    }
}






@Preview(showBackground = true)
@Composable
fun ListPreview() {

        ListScreen()

}