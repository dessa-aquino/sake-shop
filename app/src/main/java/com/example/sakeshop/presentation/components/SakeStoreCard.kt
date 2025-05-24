package com.example.sakeshop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sakeshop.R
import com.example.sakeshop.domain.model.SakeStore

@Composable
fun SakeStoreCard(
    store: SakeStore,
    onStoreClick: (SakeStore) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem da loja
            Image(
                painter = painterResource(id = R.drawable.sakeshop_placeholder),
                contentDescription = "Imagem da loja ${store.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Informações da loja
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = store.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = store.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = store.address,
                    style = MaterialTheme.typography.bodySmall
                )

                // Avaliação em estrelas
                Row {
                    repeat(5) { index ->
                        val starColor = if (index < store.rating.toInt())
                            Color.Yellow
                        else
                            Color.Gray

                        Text(
                            text = "★",
                            color = starColor,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            // Botão de link (placeholder circular)
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Link para site",
                        tint = Color.White
                    )
                }
            }
        }
    }
}