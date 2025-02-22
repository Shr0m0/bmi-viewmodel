package com.example.bmi_vm

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmi_vm.ui.theme.BmivmTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BmivmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Bmivm(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class BmiViewModel : ViewModel() {
    var heightInput by mutableStateOf("")
    var weightInput by mutableStateOf("")

    private val formatter = DecimalFormat("0.00")

    val bmi: String
        get() {
            val height = heightInput.toFloatOrNull() ?: 0.0f
            val weight = weightInput.toFloatOrNull() ?: 0.0f
            return if (weight > 0 && height > 0) {
                formatter.format(weight / height.pow(2))
            } else {
                "Invalid Input"
            }
        }
}

@Composable
fun Bmivm(modifier: Modifier = Modifier, viewModel: BmiViewModel = viewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.bmi_cal),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = viewModel.heightInput,
            onValueChange = {viewModel.heightInput = it.replace(',','.')},
            label = {Text(stringResource(R.string.height))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        OutlinedTextField(
            value = viewModel.weightInput,
            onValueChange = {viewModel.weightInput = it.replace(',','.')},
            label = {Text(stringResource(R.string.weight))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Text(
            text = stringResource(R.string.result) + " " + viewModel.bmi,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BmivmPreview() {
    BmivmTheme {
        Bmivm()
    }
}