package com.hoopCarpool.movies.presentation.util.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hoopCarpool.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.homeTopBarTitle),
                color = colorResource(
                    id = R.color.primaryTextColor
                )
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorResource(id = R.color.primaryBackground)
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun preview(){
    MyTopBar()
}