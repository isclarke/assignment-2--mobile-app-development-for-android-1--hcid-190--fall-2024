package com.example.matrixprogram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
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
          // Layout constraints
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            GreetingText(message = "Enter a Number")
            Spacer(modifier = Modifier.height(16.dp))
            MatrixInput() // Input
          }
        }
      }
    }
  }

  @Composable
  fun GreetingText(message: String, modifier: Modifier = Modifier) {
    Text(text = message, modifier = modifier)
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
        matrixSize = it.toIntOrNull() ?: 0 // Update size of matrix
      },
      label = { Text("Matrix Generator") }
    )

    Button(onClick = {
      matrixResult = generateMatrix(matrixSize) // Call generateMatrix on button click
    }) {
      Text("Generate")
    }

    if (matrixResult.isNotEmpty()) {
      Text(text = matrixResult)
    }
  }

  // Function to generate and return the matrix
  private fun generateMatrix(size: Int): String {
    if (size <= 0) {
      return "Invalid matrix size"
    }

    val highlight: String = "\u001b[33m"
    val reset: String = "\u001b[0m"
    // Create matrix array
    val matrix = Array(size) { IntArray(size) { 0 } }
    val output = StringBuilder()

    // Print the matrix with zeros
    output.append("Printing matrix with default value: \n")
    output.append(defaultMatrix(matrix, highlight, reset))

    // Print the matrix with increasing numbers
    output.append("Printing matrix: \n")
    output.append(numberMatrix(matrix, highlight, reset))

    // Print the matrix flipped
    output.append("Printing flipped matrix: \n")
    output.append(swapMatrix(matrix, highlight, reset))

    return output.toString()
  }

  fun defaultMatrix(matrix: Array<IntArray>, highlight: String, reset: String): String {
    val size = matrix.size
    val width = (size * size).toString().length
    val output = StringBuilder()

    for ((rowIndex, row) in matrix.withIndex()) {
      for ((columnIndex, num) in row.withIndex()) {
        if (columnIndex == size - 1 - rowIndex) {
          output.append("${highlight}${num.toString().padStart(width + 2)}${reset} ")
        } else {
          output.append("${num.toString().padStart(width + 2)} ")
        }
      }
      output.append("\n")
    }
    return output.toString()
  }

  fun numberMatrix(matrix: Array<IntArray>, highlight: String, reset: String): String {
    val size = matrix.size
    var count = 1
    // Width value used to get perfect spacing
    val width = (size * size).toString().length
    val output = StringBuilder()

    for ((rowIndex, row) in matrix.withIndex()) {
      for ((columnIndex, _) in row.withIndex()) {
        // Test if the index is right to left diagonal
        if (columnIndex == size - 1 - rowIndex) {
          output.append("${highlight}${count.toString().padStart(width + 2)}${reset} ")
        } else {
          output.append("${count.toString().padStart(width + 2)} ")
        }
        count++
      }
      output.append("\n")
    }
    return output.toString()
  }

  private fun swapMatrix(matrix: Array<IntArray>, highlight: String, reset: String): String {
    val size = matrix.size
    // Width value used to get perfect spacing
    val width = (size * size).toString().length
    val output = StringBuilder()

    for (rowIndex in 0 until size) {
      for (columnIndex in 0 until size) {
        val temp = (size * size) - (rowIndex * size + columnIndex)

        if (columnIndex == size - 1 - rowIndex) {
          output.append("${highlight}${(rowIndex * size + columnIndex + 1).toString().padStart(width + 2)}${reset} ")
        } else {
          output.append("${temp.toString().padStart(width + 2)} ")
        }
      }
      output.append("\n")
    }
    return output.toString()
  }

  @Preview(showBackground = true)
  @Composable
  fun MatrixPreview() {
    MatrixProgramTheme {
      Column {
        GreetingText(message = "Enter a Number")
        MatrixInput()
      }
    }
  }
}
