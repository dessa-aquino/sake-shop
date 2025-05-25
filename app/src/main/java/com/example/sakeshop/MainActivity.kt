package com.example.sakeshop

import android.content.Intent
import android.os.Bundle
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
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.sakeshop.presentation.activities.SakeStoreDetailActivity
import com.example.sakeshop.presentation.components.SakeStoreCard
import com.example.sakeshop.presentation.viewmodel.SakeStoreUiState
import com.example.sakeshop.presentation.viewmodel.SakeStoreViewModel
import com.example.sakeshop.ui.theme.SakeShopTheme
import org.koin.androidx.compose.koinViewModel



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
fun SakeStoreList(
    viewModel: SakeStoreViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is SakeStoreUiState.Loading -> {
            // Adicione um componente de loading aqui
        }
        is SakeStoreUiState.Success -> {
            LazyColumn {
                items(state.stores) { store ->
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
        }
        is SakeStoreUiState.Error -> {
            // Adicione um componente de erro aqui
        }
    }
}
