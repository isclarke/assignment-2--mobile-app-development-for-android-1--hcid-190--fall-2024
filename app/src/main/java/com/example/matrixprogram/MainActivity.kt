package com.example.matrixprogram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matrixprogram.ui.theme.MatrixProgramTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily

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
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              GreetingText(message = "Matrix Program")
              Spacer(modifier = Modifier.height(16.dp))
              MatrixInput()
            }
          }
        }
      }
    }
  }

  @Composable
  fun MatrixOutput(matrixSize: Int, matrixString: String) {
    val fontSize = if (matrixSize >= 12) 12.sp else 16.sp
    val lines = matrixString.split("\n").filter { it.isNotEmpty() }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
      for ((rowIndex, line) in lines.withIndex()) {
        Row(
          modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          val numbers = line.trim().split(" ")
          for ((columnIndex, number) in numbers.withIndex()) {
            // Check if the current position is on the diagonal (based on RED_ prefix)
            val isDiagonal = number.startsWith("RED_")
            Text(
              text = number.removePrefix("RED_").trim(),  // Remove RED_ for display
              color = if (isDiagonal) Color.Red else Color.Black, // Highlight diagonal numbers
              modifier = Modifier
                .weight(1f)
                .padding(horizontal = 1.dp), // Add padding between the columns
              textAlign = TextAlign.Center,
              fontSize = fontSize,
              fontFamily = FontFamily.SansSerif
            )
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
        matrixSize = it.toIntOrNull() ?: 0
      },
      label = { Text("Number's Only") }

    )

    Button(onClick = {
      matrixResult = generateMatrix(matrixSize)
    }) {
      Text("Generate")
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (matrixResult.isNotEmpty()) {
      MatrixOutput(matrixSize, matrixResult)
    }
  }

  private fun generateMatrix(size: Int): String {
    if (size <= 0) {
      return "Invalid matrix size"
    } else {

      val matrix = Array(size) { IntArray(size) { 0 } }
      val output = StringBuilder()

      output.append("Printing matrix with default value: \n")
      output.append(defaultMatrix(matrix))

      output.append("Printing matrix: \n")
      output.append(numberMatrix(matrix))

      output.append("Printing flipped matrix: \n")
      output.append(swapMatrix(matrix))

      return output.toString()
    }
  }

  private fun defaultMatrix(matrix: Array<IntArray>): String {
    val size = matrix.size
    val maxNumber = size * size
    val width = maxNumber.toString().length - 2
    val output = StringBuilder()

    for ((rowIndex, row) in matrix.withIndex()) {
      for ((columnIndex, num) in row.withIndex()) {
        // Change the diagonal condition to the right-to-left diagonal
        val isDiagonal = columnIndex == size - 1 - rowIndex
        output.append(
          if (isDiagonal) {
            "RED_$num".padStart(width)  // Mark diagonal numbers
          } else {
            num.toString().padStart(width)
          }
        )
        output.append(" ")
      }
      output.append("\n")
    }
    return output.toString()
  }

  private fun numberMatrix(matrix: Array<IntArray>): String {
    val size = matrix.size
    var count = 1
    val maxNumber = size * size
    val width = maxNumber.toString().length - 2
    val output = StringBuilder()

    for (rowIndex in 0 until size) {
      for (columnIndex in 0 until size) {
        // Change the diagonal condition to the right-to-left diagonal
        val isDiagonal = columnIndex == size - 1 - rowIndex
        output.append(
          if (isDiagonal) {
            "RED_$count".padStart(width)  // Mark diagonal numbers
          } else {
            count.toString().padStart(width)
          }
        )
        count++
        output.append(" ")
      }
      output.append("\n")
    }
    return output.toString()
  }

  private fun swapMatrix(matrix: Array<IntArray>): String {
    val size = matrix.size
    val output = StringBuilder()
    val maxNumber = size * size
    val width = maxNumber.toString().length - 2

    for (rowIndex in 0 until size) {
      for (columnIndex in 0 until size) {
        // Change the diagonal condition to the right-to-left diagonal
        val isDiagonal = columnIndex == size - 1 - rowIndex

        // If it's a diagonal element, keep its original value
        val value = if (isDiagonal) {
          "RED_${(rowIndex * size + columnIndex + 1)}" // Mark diagonal values
        } else {
          (maxNumber - (rowIndex * size + columnIndex)).toString()
        }

        output.append(value.padStart(width) + " ")
      }
      output.append("\n")
    }
    return output.toString()
  }
}
