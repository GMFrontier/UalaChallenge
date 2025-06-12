package com.frommetoyou.core_ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MyToolbar(
    title: String = "Back",
    onBackClick: (() -> Unit)? = { }
) {
    TopAppBar(
        title = { Text(text = title, fontSize = 20.sp, color = Color.Black) },
        navigationIcon = {
            onBackClick?.let {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White
        )
    )
}