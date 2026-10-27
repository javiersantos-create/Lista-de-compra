package com.javier.proyectointermodular.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javier.proyectointermodular.ui.clases.RepositorioCompra

@Composable
fun PantallaLista(
    idLista: String,
    nombreLista: String,
    repositorio: RepositorioCompra,
    onVolverAtras: () -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var textoNuevoProducto by remember { mutableStateOf("") }

    // Escuchamos los productos de esta lista cuando entramos
    LaunchedEffect(idLista) {
        repositorio.escucharProductosDeLista(idLista)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarDialogo = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir producto")
            }
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .padding(16.dp)
        ) {
            // Botón volver e imagen
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onVolverAtras() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text(text = nombreLista, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (repositorio.productosDeLaLista.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NO HAY PRODUCTOS AÑADIDOS",
                        fontSize = 18.sp,
                        modifier = Modifier.alpha(0.4f)
                    )
                }
            } else {
                LazyColumn {
                    items(repositorio.productosDeLaLista) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = producto.nombre)
                                IconButton(
                                    onClick = { repositorio.borrarProductoDeLista(idLista, producto.id) }
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar producto")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Nuevo Producto") },
            text = {
                OutlinedTextField(
                    value = textoNuevoProducto,
                    onValueChange = { textoNuevoProducto = it },
                    label = { Text("Nombre del producto") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        repositorio.anadirProductoALista(idLista, textoNuevoProducto)
                        textoNuevoProducto = ""
                        mostrarDialogo = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}