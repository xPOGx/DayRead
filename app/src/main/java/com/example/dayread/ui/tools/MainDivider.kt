package com.example.dayread.ui.tools

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.dayread.R

@Composable
fun MainDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        thickness = dimensionResource(R.dimen.tiny),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.fillMaxWidth()
    )
}