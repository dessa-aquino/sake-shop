package com.example.sakeshop.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sakeshop.domain.model.SakeStore
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.res.painterResource
import com.example.sakeshop.R
import androidx.compose.foundation.clickable
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SakeStoreDetail(
    store: SakeStore,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { DetailTopBar(onNavigateBack) },
        bottomBar = { DetailBottomBar(store) }
    ) { _ ->
        Box( modifier = Modifier
            .fillMaxSize()
        ) {

            ImageHeader(store)

            Column( modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(180.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .offset(y = (-20).dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {

                        NameAndDescriptionSection(store)
                        Spacer(modifier = Modifier.height(16.dp))
                        AddressSection(store)
                        Spacer(modifier = Modifier.height(16.dp))
                        RatingSection(store)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center
            ) {

                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = Color.Black.copy(alpha = 0.7f)
                ) { }


                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomBar(
    store: SakeStore
) {
    val context = LocalContext.current
    if (store.website.isNotBlank()) {
        Button(
            onClick = {
                openBrowser(context, store.website)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Open site")
        }
    }
}

@Composable
fun ImageHeader(store: SakeStore) {
    val imageModel = when (store.picture) {
        null -> R.drawable.placeholder_image2
        else -> store.picture
    }

    AsyncImage(
        model = imageModel,
        contentDescription = "Store picture ${store.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        error = painterResource(id = R.drawable.placeholder_image2),
        fallback = painterResource(id = R.drawable.placeholder_image2)
    )
}

@Composable
fun NameAndDescriptionSection(store: SakeStore) {
    Text(
        text = store.name,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = store.description,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun AddressSection(store: SakeStore) {

    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = store.address,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .weight(1f)
                .clickable { openGoogleMaps(context, store.googleMapsLink) }
        )
    }
}

@Composable
fun RatingSection(store: SakeStore) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = store.rating.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

private fun openGoogleMaps(context: Context, googleMapsLink: String) {
    val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsLink.toUri()).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            openBrowser(context, googleMapsLink)
        }
    } catch (e: Exception) {
        openBrowser(context, googleMapsLink)
    }
}

private fun openBrowser(context: Context, link: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, link.toUri())
    context.startActivity(browserIntent)
}