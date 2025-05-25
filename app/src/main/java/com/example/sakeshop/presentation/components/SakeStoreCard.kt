package com.example.sakeshop.presentation.components

import coil.compose.AsyncImage
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
import androidx.compose.foundation.clickable
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sakeshop.R
import com.example.sakeshop.domain.model.SakeStore



@Composable
fun SakeStoreCard(
    store: SakeStore,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem da loja
            store.picture?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Imagem da loja ${store.name}",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

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

                // Rating
                Row {
                    Text(
                        text = "★ ${store.rating}",
                        color = Color.Yellow,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}