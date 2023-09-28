package com.example.dayread.ui.tools

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dayread.R

@Composable
fun ErrorBox(
    modifier: Modifier = Modifier,
    tryAgain: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.error_2to3),
                contentDescription = stringResource(R.string.error_loading_data),
                modifier = Modifier
                    .size(dimensionResource(R.dimen.large))
                    .background(MaterialTheme.colorScheme.error, CircleShape)
            )
            OutlinedButton(
                onClick = tryAgain,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.medium))
            ) {
                Text(
                    text = stringResource(R.string.try_again),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun ErrorPreview(

) {
    ErrorBox()
}