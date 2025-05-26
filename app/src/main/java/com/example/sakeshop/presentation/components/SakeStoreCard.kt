package com.example.sakeshop.presentation.components

import coil.compose.AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.LocationOn


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
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.5.dp,
            hoveredElevation = 1.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {

            CardImage(store)

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardStoreName(store)
                CardAddress(store)
                RatingSection(store)
            }
        }
    }
}

@Composable
fun CardImage(store: SakeStore) {
    val imageModel = when (store.picture) {
        null -> R.drawable.placeholder_image
        else -> store.picture
    }

    AsyncImage(
        model = imageModel,
        contentDescription = "Store image ${store.name}",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.placeholder_image),
        fallback = painterResource(id = R.drawable.placeholder_image)
    )
}

@Composable
fun CardStoreName(store: SakeStore) {
    Text(
        text = store.name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun CardAddress(store: SakeStore) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = store.address,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
