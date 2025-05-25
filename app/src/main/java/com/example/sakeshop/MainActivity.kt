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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.height
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.remember
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.ui.Alignment




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
            Log.e("AppInit", "Erro de inicialização", e)
            Toast.makeText(this, "Erro ao iniciar o aplicativo: ${e.message}", Toast.LENGTH_LONG).show()
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
fun SakeStoreList(
    viewModel: SakeStoreViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            HomeHeader()
        }

        when (val state = uiState.value) {
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
                        }
                    )
                }
            }
            is SakeStoreUiState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Carregando...")
                    }
                }
            }
            is SakeStoreUiState.Error -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

