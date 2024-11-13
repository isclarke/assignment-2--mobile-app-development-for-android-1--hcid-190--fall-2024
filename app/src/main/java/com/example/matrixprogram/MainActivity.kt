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

  private fun Button(onClick: () -> Unit, modifier: (Int) -> Unit) {

  }

  @Composable
  fun MatrixInput() {
    var text by remember { mutableStateOf("")  }
    var matrixSize by remember { mutableStateOf(0) }
    var matrixResult by remember { mutableStateOf("") }

    TextField(
      value = text,
      onValueChange = {
        text = it
        matrixSize = it.toIntOrNull() ?: 0 // update size of matrix
      },
      label = {Text("Matrix Generator")}
    )

    Button(onClick = {
      matrixResult = "Your matrix is: ${matrixSize} x ${matrixSize}"
        generateMatrix(matrixSize)
    }

    fun generateMatrix() {

      val HIGHLIGHT: String = "\u001b[33m"
      val RESET: String = "\u001b[0m"

      val matrix = Array(number) { IntArray(number) { 0 } }

      //Print the matrix with zeros
      println("Printing matrix with deafult value:")
      defaultMatrix(matrix, HIGHLIGHT, RESET)

      //Print the matrix with increasing numbers
      println("Printing matrix:")
      numberMatrix(matrix, HIGHLIGHT, RESET)

      //Print the matrix flipped
      println("Printing flipped matrix:")
      swapMatrix(matrix, HIGHLIGHT, RESET)

      //Function to get deafult matrix with zeros
      fun defaultMatrix(matrix: Array<IntArray>, HIGHLIGHT: String, RESET: String) {
        val size = matrix.size
        //Width value used to get perfect spacing
        val width = (size * size).toString().length

        for((rowIndex, row) in matrix.withIndex()) {
          for((columnIndex, num) in row.withIndex()) {
            if (columnIndex == size - 1 - rowIndex) {
              print("${HIGHLIGHT}${num.toString().padStart(width + 2)}${RESET} ")
            }
            else {
              print("${num.toString().padStart(width + 2)} ")
            }
          }
          println("")
        }
      }

      fun numberMatrix(matrix: Array<IntArray>, HIGHLIGHT: String, RESET: String) {
        val size = matrix.size
        var count: Int = 1
        //Width value used to get perfect spacing
        val width = (size * size).toString().length

        for((rowIndex, row) in matrix.withIndex()) {
          for((columnIndex, i) in row.withIndex()) {
            //Test if the index is right to left diagonal
            if(columnIndex == size - 1 - rowIndex) {
              print("${HIGHLIGHT}${count.toString().padStart(width + 2)}${RESET} ")
            }
            //If it's not it doesnt get a highlight
            else {
              print("${count.toString().padStart(width + 2)} ")
            }
            count++
          }
          println("")
        }
      }

      fun swapMatrix(matrix: Array<IntArray>, HIGHLIGHT: String, RESET: String) {
        val size = matrix.size
        //Create temp variable
        var temp: Int
        //Width value used to get perfect spacing
        val width = (size * size).toString().length

        for((rowIndex, row) in matrix.withIndex()) {
          for((columnIndex, i) in row.withIndex()) {
            temp = (size * size) - (rowIndex * size + columnIndex)

            if(columnIndex == size - 1 - rowIndex) {
              print("${HIGHLIGHT}${(rowIndex * size + columnIndex + 1).toString().padStart(width + 2)}${RESET} ")
            }
            else {
              print("${temp.toString().padStart(width + 2)} ")
            }
            println("")
          }
        }

      }


    }    }


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
