package com.example.dayread.ui.tools

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.dimensionResource
import com.example.dayread.R

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round,
            modifier = Modifier.size(dimensionResource(R.dimen.large))
        )
    }
}