package com.example.dayread

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dayread.ui.theme.DayReadTheme
import com.example.dayread.ui.tools.Constants.STRING_KEY

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DayReadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DayReadApp(
                        linkWebView = { startWeb(it) }
                    )
                }
            }
        }
    }

    private fun startWeb(link: String) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(STRING_KEY, link)
        startActivity(intent)
    }
}
