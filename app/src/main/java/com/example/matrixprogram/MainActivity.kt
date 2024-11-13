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
              GreetingText(message = "Enter a Number")
              Spacer(modifier = Modifier.height(16.dp))
              MatrixInput()
            }
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
  fun MatrixWithColoredDiagonals(matrixSize: Int, matrixString: String) {
    val lines = matrixString.split("\n").filter { it.isNotEmpty() }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
      for ((rowIndex, line) in lines.withIndex()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          val numbers = line.trim().split(" ")
          for ((columnIndex, number) in numbers.withIndex()) {
            val isDiagonal = columnIndex == rowIndex || columnIndex == matrixSize - 1 - rowIndex
            val textColor = if (isDiagonal) Color.Red else Color.Black
            Text(
              text = number,
              color = textColor,
              modifier = Modifier
                .padding(4.dp)
                .weight(1f)
                .wrapContentWidth(),
              textAlign = TextAlign.Center
            )
          }
        }
      }
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
        matrixSize = it.toIntOrNull() ?: 0
      },
      label = { Text("Matrix Generator") }
    )

    Button(onClick = {
      matrixResult = generateMatrix(matrixSize)
    }) {
      Text("Generate")
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (matrixResult.isNotEmpty()) {
      MatrixWithColoredDiagonals(matrixSize, matrixResult)
    }
  }

  private fun generateMatrix(size: Int): String {
    if (size <= 0) {
      return "Invalid matrix size"
    }

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
  private fun defaultMatrix(matrix: Array<IntArray>): String {
    val size = matrix.size
    val maxNumber = size * size
    val width = maxNumber.toString().length
    val output = StringBuilder()

    for ((rowIndex, row) in matrix.withIndex()) {
      for ((columnIndex, num) in row.withIndex()) {
        output.append("${num.toString().padStart(width + 2)} ")
      }
      output.append("\n")
    }
    return output.toString()
  }

  private fun numberMatrix(matrix: Array<IntArray>): String {
    val size = matrix.size
    var count = 1
    val maxNumber = size * size
    val width = maxNumber.toString().length
    val output = StringBuilder()

    for (rowIndex in 0 until size) {
      for (columnIndex in 0 until size) {
        output.append("${count.toString().padStart(width + 2)} ")
        count++
      }
      output.append("\n")
    }
    return output.toString()
  }

  private fun swapMatrix(matrix: Array<IntArray>): String {
    val size = matrix.size
    val output = StringBuilder()
    val maxNumber = size * size
    val width = maxNumber.toString().length

    for (rowIndex in 0 until size) {
      for (columnIndex in 0 until size) {
        val temp = (size * size) - (rowIndex * size + columnIndex)
        output.append("${temp.toString().padStart(width + 2)} ")
      }
      output.append("\n")
    }
    return output.toString()
  }
}
