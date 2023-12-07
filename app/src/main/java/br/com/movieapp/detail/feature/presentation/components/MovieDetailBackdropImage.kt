package br.com.movieapp.detail.feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.movieapp.core.presentation.components.common.AsyncImageUrl

@Composable
fun MovieDetailBackdropImage(
    imageUrl: String,
    modifier: Modifier,
) {
    Box(modifier = modifier) {
        AsyncImageUrl(
            imageUrl = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
fun MovieDetailBackdropImagePreview() {
    MovieDetailBackdropImage(
        imageUrl = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    )
}