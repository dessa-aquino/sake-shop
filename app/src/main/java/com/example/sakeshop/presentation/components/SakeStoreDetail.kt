package com.example.sakeshop.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SakeStoreDetail(
    store: SakeStore,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Background circular com efeito "blur"
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(15.dp),
                            color = Color.Black.copy(alpha = 0.7f)
                        ) { }

                        // Botão de voltar
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Voltar",
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
    ) { _ ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            val imageModel = when (store.picture) {
                null -> R.drawable.placeholder_image2
                else -> store.picture
            }

            // A imagem agora ocupa toda a tela, incluindo a área da TopBar
            AsyncImage(
                model = imageModel,
                contentDescription = "Foto da loja ${store.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                error = painterResource(id = R.drawable.placeholder_image2),
                fallback = painterResource(id = R.drawable.placeholder_image2)
            )

            // O conteúdo principal agora começa do topo da tela
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Espaço para compensar a altura da TopBar e manter o conteúdo visível
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


                        Text(
                            text = store.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Informações de endereço
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
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { openGoogleMaps(context, store.googleMapsLink) }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Descrição
                        store.description?.let { description ->
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun openGoogleMaps(context: Context, googleMapsLink: String) {
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsLink)).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        // Tenta abrir no Google Maps primeiro
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            // Se não tiver o Maps instalado, abre no navegador
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsLink))
            context.startActivity(browserIntent)
        }
    } catch (e: Exception) {
        // Em caso de qualquer erro, tenta abrir no navegador como última opção
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsLink))
        context.startActivity(browserIntent)
    }
}
