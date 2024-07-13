package com.example.alwa.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alwa.data.model.AppMetric
import com.example.alwa.ui.theme.AlwaTheme
import com.example.alwa.util.dateToString
import com.example.alwa.data.local.LocalAppsDataProvider

@Composable
fun AppDetail(
    modifier: Modifier, upPress: () -> Unit, appMetric: AppMetric,
    onDelete: (Int) -> Unit
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                    IconButton(onClick = { upPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(
                        text = appMetric.app!!.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = appMetric.app!!.description
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Observaciones", style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .height(1.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Absolute.Left,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Obsoleto",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Switch(checked = appMetric.obsolete, onCheckedChange = {})
                        }
                    }

                    if (appMetric.obsolete) {
                        IconButton(onClick = {
                            onDelete(appMetric.app!!.id)
                            Toast.makeText(context, "Aplicación eliminada!", Toast.LENGTH_SHORT)
                                .show()
                            upPress()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "icon",
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "La última actualización de esta aplicación fue el ${appMetric.app!!.lastRelease.dateToString()}"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Recomendaciones", style = MaterialTheme.typography.titleSmall
                )

                val recommendationText = if (appMetric.obsolete) {
                    "Podrías considerar eliminar esta aplicación obsoleta. " +
                            "Ó considerar revisar e implementar cambios adoptando las nuevas tecnologías."
                } else if (appMetric.lowRating) {
                    "Para aumentar la experiencia de usuario, " +
                            "podrías considerar revisar e implementar cambios adoptando las nuevas tecnologías."
                } else {
                    "La aplicación está al día."
                }

                Text(
                    text = recommendationText
                )
                if (appMetric.level != 1) {
                    LazyColumn {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        items(
                            items = if (appMetric.obsolete)
                                LocalAppsDataProvider.recommendationsByObsolescence
                            else
                                LocalAppsDataProvider.recommendationsByLowRating
                        ) { item ->
                            Text(text = item)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun AppDetailPreview() {
    AlwaTheme {
        AppDetail(
            modifier = Modifier,
            upPress = {},
            appMetric = AppMetric(),
            onDelete = {}
        )
    }
}