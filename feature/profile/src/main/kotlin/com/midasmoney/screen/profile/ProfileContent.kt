package com.midasmoney.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileContentImp(paddingValues: PaddingValues) {
    val profileViewModel = ProfileViewModel()
    ProfileContent(paddingValues, profileViewModel.text.value.toString())
}

@Composable
fun ProfileContent(paddingValues: PaddingValues, text: String) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentImpPreview() {
    val profileViewModel = ProfileViewModel()
    val paddingValues = PaddingValues()
    ProfileContent(paddingValues, profileViewModel.text.value.toString())
}