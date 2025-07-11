package com.midasmoney.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeContentImp() {
    val homeViewModel = HomeViewModel()
    HomeContent(homeViewModel.text.value.toString())
}

@Composable
fun HomeContent(text: String) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            BalanceStatus()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentImpPreview(){
    val homeViewModel = HomeViewModel()
    HomeContent(homeViewModel.text.value.toString())
}