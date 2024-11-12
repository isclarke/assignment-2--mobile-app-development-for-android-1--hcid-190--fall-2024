package com.example.matrixprogram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.matrixprogram.ui.theme.MatrixProgramTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MatrixProgramTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          //layout constraints
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp), 

        }
      }
    }
  }
}

@Composable
fun GreetingText(message: String, modifier: Modifier = Modifier) {
  Text(
    text = message,
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun MatrixPreview() {
  MatrixProgramTheme {
    GreetingText(message = "Enter a Number")
  }
}
