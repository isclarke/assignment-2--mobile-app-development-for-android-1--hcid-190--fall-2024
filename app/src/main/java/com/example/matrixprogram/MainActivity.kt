package com.example.matrixprogram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            GreetingText(message = "Enter a Number")
            Spacer(modifier = Modifier.height(16.dp))
            MatrixInput() //input
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

  }

  @Composable
  fun MatrixInput() {
    var text by remember { mutableStateOf("") }
    var matrixSize by remember { mutableStateOf(0) }
    var matrixResult by remember { mutableStateOf("") }

    TextField(
      value = text,
      onValueChange = {
        text = it
        matrixSize = it.toIntOrNull() ?: 0 // update size of matrix
      },
      label = { Text("Matrix Generator") }
    )

    Button(onClick = {
      matrixResult = "Your matrix is: ${matrixSize} x ${matrixSize}"
      generateMatrix(matrixSize)
    }) {
  Text("Generate")
  }
    if (matrixResult.isNotEmpty()) {
      Text(text = matrixResult)
    }

    fun generateMatrix() {
      val HIGHLIGHT: String = "\u001b[33m"
      val RESET: String = "\u001b[0m"
      val number = matrixSize
      //Create matrix array
      val matrix = Array(number) { IntArray(number) { 0 } }
      var output = StringBuilder()

      //Print the matrix with zeros
      output.append("Printing matrix with deafult value: \n")
      output.append(defaultMatrix(matrix, HIGHLIGHT, RESET))

      //Print the matrix with increasing numbers
      output.append("Printing matrix: \n")
      output.append(numberMatrix(matrix, HIGHLIGHT, RESET))

      //Print the matrix flipped
      output.append("Printing flipped matrix: \n ")
      output.append(swapMatrix(matrix, HIGHLIGHT, RESET))



    }
    @Preview(showBackground = true)
    @Composable
    fun MatrixPreview() {
      MatrixProgramTheme {
        Column {
          MainActivity().GreetingText(message = "Enter a Number")
          MainActivity().MatrixInput()
        }
      }
    }
  }
