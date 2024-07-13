package com.example.alwa.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alwa.data.model.App
import com.example.alwa.data.model.AppMetric
import com.example.alwa.ui.theme.AlwaTheme
import com.example.alwa.util.isValidRating
import com.example.alwa.util.longToStringDate
import com.example.alwa.util.stringToDate
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterNewApp(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    onAdd: (AppMetric) -> Unit
) {
    val context = LocalContext.current
    val uploadDateState = rememberDatePickerState()
    val releaseUpState = rememberDatePickerState()

    var name by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var downloads by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showDatePickerRelease by remember { mutableStateOf(false) }

    var uploadDate = uploadDateState.selectedDateMillis?.longToStringDate() ?: ""
    var releaseUp = releaseUpState.selectedDateMillis?.longToStringDate() ?: ""

    var isRatingError by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 24.dp),
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = { upPress() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "icon",
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                    Text(
                        text = "Registrar aplicación",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp, start = 24.dp, top = 62.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(text = "Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = {
                        Text(text = "Descripción")
                    },
                )
                OutlinedTextField(
                    value = rating,
                    onValueChange = {
                        if (it.length <= 3) {
                            rating = it
                            isRatingError = !isValidRating(rating)
                        }
                    },
                    placeholder = { Text(text = "Valoración") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = {
                        Text(text = "Valoración")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = isRatingError
                )
                OutlinedTextField(
                    value = downloads,
                    onValueChange = { downloads = it },
                    placeholder = { Text(text = "Descargas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = {
                        Text(text = "Descargas")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = uploadDate,
                    onValueChange = { uploadDate = it },
                    placeholder = { Text(text = "Fecha de publicación") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = {
                        Text(text = "Fecha de publicación")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.DateRange,
                            contentDescription = "date",
                            modifier = Modifier.clickable {
                                showDatePicker = true
                                showDatePickerRelease = false
                            })
                    }
                )
                OutlinedTextField(
                    value = releaseUp,
                    onValueChange = { releaseUp = it },
                    placeholder = { Text(text = "Fecha de actualización") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = {
                        Text(text = "Fecha de actualización")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.DateRange,
                            contentDescription = "date",
                            modifier = Modifier.clickable {
                                showDatePicker = true
                                showDatePickerRelease = true
                            })
                    }
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = {
                            showDatePicker = false
                        }, confirmButton = {
                            Button(onClick = { showDatePicker = false }) {
                                Text(text = "OK")
                            }
                        }
                    ) {
                        DatePicker(state = if (showDatePickerRelease) releaseUpState else uploadDateState)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                FilledTonalButton(
                    onClick = {
                        onAdd(
                            AppMetric(
                                App(
                                    Random.nextInt(10, 100),
                                    name,
                                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                                    rating.toFloat(),
                                    downloads.toInt(),
                                    uploadDate.stringToDate()!!,
                                    if (releaseUp.isEmpty()) uploadDate.stringToDate()!! else releaseUp.stringToDate()!!
                                )
                            )
                        )
                        Toast.makeText(context, "Aplicación registrada!", Toast.LENGTH_SHORT).show()
                        upPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    enabled = name.isNotEmpty() && rating.isNotEmpty() &&
                            downloads.isNotEmpty() && uploadDate.isNotEmpty() &&
                            !isRatingError
                ) {
                    Text(text = "Registrar")
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RegisterNewAppPreview() {
    AlwaTheme {
        RegisterNewApp(
            upPress = {},
            onAdd = {}
        )
    }
}