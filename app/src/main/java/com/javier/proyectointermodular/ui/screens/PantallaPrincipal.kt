package com.javier.proyectointermodular.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javier.proyectointermodular.ui.clases.ListaCompra
import com.javier.proyectointermodular.ui.clases.RepositorioCompra

@Composable
fun PantallaPrincipal(
    repositorio: RepositorioCompra,
    onAbrirLista: (ListaCompra) -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var textoNuevaLista by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarDialogo = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir lista")
            }
        }
    ) { contenidoPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contenidoPadding)
                .padding(16.dp)
        ) {
            Text(text = "Mis Listas", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Comprobamos la lista de LISTAS, no la de productos
            if (repositorio.listas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CREA ALGUNA LISTA",
                        fontSize = 20.sp,
                        modifier = Modifier.alpha(0.4f)
                    )
                }
            } else {
                LazyColumn {
                    items(repositorio.listas) { lista ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onAbrirLista(lista) } // Hacemos clic para abrir la lista
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = lista.nombre)

                                IconButton(
                                    onClick = { repositorio.borrarLista(lista.id) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Borrar"
                                    )
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
            title = { Text("Nueva lista") },
            text = {
                OutlinedTextField(
                    value = textoNuevaLista,
                    onValueChange = { textoNuevaLista = it },
                    label = { Text("Nombre Lista") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        repositorio.crearLista(textoNuevaLista)
                        textoNuevaLista = ""
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