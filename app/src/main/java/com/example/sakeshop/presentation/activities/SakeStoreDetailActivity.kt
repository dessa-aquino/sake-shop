package com.example.sakeshop.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sakeshop.ui.theme.SakeShopTheme
import com.example.sakeshop.presentation.viewmodel.SakeStoreViewModel
import com.example.sakeshop.presentation.components.SakeStoreDetail
import org.koin.androidx.viewmodel.ext.android.viewModel



class SakeStoreDetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_STORE_NAME = "store_name"
    }

    private val viewModel: SakeStoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storeName = intent.getStringExtra(EXTRA_STORE_NAME)

        setContent {
            SakeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SakeStoreDetailScreen(
                        storeName = storeName ?: "",
                        onLinkClick = {
                            val url = viewModel.getStoreUrl()
                            Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
                                startActivity(it)
                            }
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
fun SakeStoreDetailScreen(
    storeName: String, // ou o tipo de ID que você está usando
    onLinkClick: () -> Unit,
    viewModel: SakeStoreViewModel // seu ViewModel
) {
    // Obter os detalhes da loja do ViewModel
    val store = viewModel.getStoreDetails(storeName)

    SakeStoreDetail(
        store = store,
        onLinkClick = onLinkClick
    )
}
