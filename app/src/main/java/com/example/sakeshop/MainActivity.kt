package com.example.sakeshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.sakeshop.presentation.activities.SakeStoreDetailActivity
import com.example.sakeshop.presentation.components.SakeStoreCard
import com.example.sakeshop.presentation.viewmodel.SakeStoreUiState
import com.example.sakeshop.presentation.viewmodel.SakeStoreViewModel
import com.example.sakeshop.ui.theme.SakeShopTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                SakeShopTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SakeStoreList()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AppInit", "Error on initialization", e)
            Toast.makeText(this, "Something wrong.", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun SakeStoreList(
    viewModel: SakeStoreViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                HomeHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            when (val state = uiState) {
                is SakeStoreUiState.Loading -> item { LoadingContent() }
                is SakeStoreUiState.Error -> item { ErrorContent(state.message) }
                is SakeStoreUiState.Success -> {
                    items(
                        items = state.stores,
                        key = { it.name }
                    ) { store ->
                        SakeStoreCard(
                            store = store,
                            onItemClick = {
                                val intent = Intent(context, SakeStoreDetailActivity::class.java).apply {
                                    putExtra(SakeStoreDetailActivity.EXTRA_STORE_NAME, store.name)
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(bottomStart = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sake Shop",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Discover authentic Japanese sake shops with traditional flavors",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Error, but don´t feel sad. Try again!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}