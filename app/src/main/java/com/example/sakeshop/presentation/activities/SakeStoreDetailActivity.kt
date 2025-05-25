package com.example.sakeshop.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class SakeStoreDetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_STORE_NAME = "store_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storeName = intent.getStringExtra(EXTRA_STORE_NAME)

        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Detalhes da loja: $storeName")
            }
        }
    }
}
