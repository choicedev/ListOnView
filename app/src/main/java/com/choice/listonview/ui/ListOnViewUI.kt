package com.choice.listonview.ui

import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.choice.listonview.ListViewModel
import com.choice.listonview.di.model.Ticket
import com.choice.listonview.ui.model.ListEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun ListOnViewUI(modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<ListViewModel>()
    val state by viewModel.state.collectAsState()
    val event = state.event


    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (event) {
                is ListEvent.ListAll -> {
                    LazyColumn(
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxSize()
                    ) {
                        items(event.list) { ticket ->
                            CardItem(
                                modifier = Modifier.animateContentSize(),
                                item = ticket,
                                onClick = { id ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Item $id clicked")
                                    }
                                },
                                onItemUpdated = { id ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Item $id updated")
                                    }
                                }
                            )
                        }
                    }
                }

                is ListEvent.Loading -> {
                    InfiniteLinearProgressIndicator(
                        modifier = Modifier.width(100.dp)
                    )
                }

            }

        }
    }
}


@Composable
fun InfiniteLinearProgressIndicator(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Looking for tickets at the db..",
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        style = MaterialTheme.typography.bodyMedium,
    )
    LinearProgressIndicator(
        modifier = modifier
            .padding(top = 10.dp)
            .clip(CircleShape)
            .animateContentSize()
    )
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier, item: Ticket,
    onClick: (id: Int) -> Unit,
    onItemUpdated: (id: Int) -> Unit,
) {

    if(item.isChanged){
        onItemUpdated(item.id)
    }

    Card(modifier = modifier
        .padding(vertical = 5.dp)
        .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = {
            onClick(item.id)
        }) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                            CircleShape
                        ),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(5.dp)
                            .padding(horizontal = 5.dp),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        text = "${item.id}"
                    )
                }

                Box(
                    modifier = Modifier.padding(1.dp)
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    text = item.date
                )
            }

            Text(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleLarge,
                text = item.title
            )

            Text(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodySmall,
                text = item.description
            )

            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        onClick(item.id)
                    }
                ) {
                    Text(
                        text = if (item.isDownloaded) "Join" else "Download"
                    )
                }
            }
        }
    }


}