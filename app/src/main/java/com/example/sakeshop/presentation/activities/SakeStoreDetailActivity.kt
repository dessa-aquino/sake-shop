package com.example.sakeshop.presentation.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.sakeshop.ui.theme.SakeShopTheme
import com.example.sakeshop.presentation.components.SakeStoreDetail
import com.example.sakeshop.presentation.viewmodel.SakeStoreUiState
import com.example.sakeshop.presentation.viewmodel.SakeStoreViewModel
import org.koin.androidx.compose.koinViewModel


class SakeStoreDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storeName = intent.getStringExtra(EXTRA_STORE_NAME) ?: ""

        try {
            setContent {
                SakeShopTheme {
                    SakeStoreDetailScreenContainer(storeName = storeName)
                }
            }
        } catch (e: Exception) {
            Log.e("DetailScreenInit", "Error on detail screen presentation", e)
            Toast.makeText(this, "Something wrong.", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val EXTRA_STORE_NAME = "store_name"
    }
}

@Composable
private fun SakeStoreDetailScreenContainer(storeName: String) {
    val viewModel: SakeStoreViewModel = koinViewModel()
    viewModel.loadStoreDetails(storeName)
    SakeStoreDetailScreen(viewModel = viewModel)
}

@Composable
private fun SakeStoreDetailScreen(
    viewModel: SakeStoreViewModel
) {
    val store by viewModel.selectedStore.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    when (val currentState = uiState) {
        is SakeStoreUiState.Error -> {
            Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
        }
        is SakeStoreUiState.Loading -> {
            CircularProgressIndicator()
        }
        is SakeStoreUiState.Success -> {
            store?.let { sakeStore ->
                SakeStoreDetail(
                    store = sakeStore,
                    onNavigateBack = {
                        (context as? Activity)?.finish()
                    }
                )
            }
        }
    }
}