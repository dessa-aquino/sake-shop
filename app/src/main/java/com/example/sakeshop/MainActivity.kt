package com.example.sakeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.util.Log
import android.widget.Toast
import com.example.sakeshop.data.repository.SakeStoreRepository
import com.example.sakeshop.presentation.components.SakeStoreCard
import com.example.sakeshop.ui.theme.SakeShopTheme

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
            Log.e("AppInit", "Erro de inicialização: ${e.message}")
            Toast.makeText(this, "Erro ao iniciar o aplicativo", Toast.LENGTH_LONG).show()
        }



    }
}

@Composable
fun SakeStoreList() {
    val repository = SakeStoreRepository()
    val stores = repository.getSakeStores()

    LazyColumn {
        items(stores) { store ->
            SakeStoreCard(store)
        }
    }
}