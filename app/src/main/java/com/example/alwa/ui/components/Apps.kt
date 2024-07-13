package com.example.alwa.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alwa.data.model.AppMetric
import com.example.alwa.ui.AppUIState
import com.example.alwa.ui.theme.AlwaTheme
import com.example.alwa.ui.theme.Severe
import com.example.alwa.ui.theme.Success
import com.example.alwa.ui.theme.Warn
import com.example.alwa.ui.theme.Yellow

@Composable
fun Apps(
    appUIState: AppUIState,
    modifier: Modifier,
    addNewApp: () -> Unit,
    onDetail: (AppMetric) -> Unit
) {
    LazyColumn(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Aplicaciones",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = { addNewApp() }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "icon",
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(items = appUIState.apps, key = { it.app!!.id }) { app ->
            AppItem(
                appMetric = app,
                onDetail = { onDetail(it) }
            )
        }
    }
}

@Composable
fun AppItem(
    appMetric: AppMetric,
    onDetail: (AppMetric) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(CardDefaults.shape)
            .clickable { onDetail(appMetric) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = appMetric.app!!.name,
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(0.25f),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "icon",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopCenter)
                                .padding(end = 4.dp),
                            tint = Yellow
                        )
                        Text(
                            text = appMetric.app!!.rating.toString(),
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                }
            }
            Text(
                text = appMetric.app!!.description, style = MaterialTheme.typography.titleSmall
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "icon",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.End),
                tint = when (appMetric.level) {
                    1 -> Success
                    2 -> Warn
                    else -> Severe
                }
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun AppsPreview() {
    AlwaTheme {
        Apps(
            appUIState = AppUIState(),
            modifier = Modifier,
            addNewApp = {},
            onDetail = {}
        )
    }
}