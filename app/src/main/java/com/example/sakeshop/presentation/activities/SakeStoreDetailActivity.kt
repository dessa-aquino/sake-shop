package com.example.sakeshop.presentation.activities

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.sakeshop.ui.theme.SakeShopTheme
import com.example.sakeshop.presentation.components.SakeStoreDetail
import com.example.sakeshop.presentation.viewmodel.SakeStoreViewModel
import org.koin.androidx.compose.koinViewModel


class SakeStoreDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storeName = intent.getStringExtra(EXTRA_STORE_NAME) ?: ""

        setContent {
            SakeShopTheme {
                SakeStoreDetailScreenContainer(storeName = storeName)
            }
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
    val context = LocalContext.current

    store?.let { sakeStore ->
        SakeStoreDetail(
            store = sakeStore,
            onNavigateBack = {
                (context as? Activity)?.finish()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}


