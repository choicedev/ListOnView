package com.choice.listonview.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.choice.listonview.ListViewModel
import com.choice.listonview.di.model.Ticket
import com.choice.listonview.ui.model.ListEvent
import kotlinx.coroutines.CoroutineScope
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
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val ticketUpdate = remember {
        mutableStateOf<Ticket?>(null)
    }


    LaunchedEffect(state.snackBarMessage) {
        state.snackBarMessage?.let { ticket ->
            val isDownloaded = if (ticket.isDownloaded) "Join" else "Download"
            ticketUpdate.value = ticket
            snackbarHostState.showSnackbar(
                message = "Ticket ${ticket.id} to $isDownloaded",
                actionLabel = "Go To Ticket: ${ticket.id}",
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                SnackbarList(scope, event, listState, ticketUpdate, data)
            }
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
                        state = listState,
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxSize()
                    ) {
                        items(event.list, key = {
                            it.id
                        }) { ticket ->
                            CardItem(
                                modifier = Modifier.animateContentSize(),
                                item = ticket,
                                onClick = { id ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Item $id clicked")
                                    }
                                },
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
private fun SnackbarList(
    scope: CoroutineScope,
    event: ListEvent,
    listState: LazyListState,
    ticketUpdate: MutableState<Ticket?>,
    data: SnackbarData
) {
    Snackbar(
        modifier = Modifier
            .padding(16.dp),
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
        action = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                onClick = {
                    scope.launch {
                        if (event is ListEvent.ListAll) {
                            listState.scrollToItem(event.list.indexOf(ticketUpdate.value))
                        }
                    }
                }) {
                Text(text = data.visuals.actionLabel ?: "")
            }
        }
    ) {
        Text(text = data.visuals.message)
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
        color = MaterialTheme.colorScheme.primary,
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
) {

    Log.d("ListOnViewUI", "CardItem: $item")

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