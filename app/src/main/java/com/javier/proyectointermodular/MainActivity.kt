package com.javier.proyectointermodular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.javier.proyectointermodular.ui.screens.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginSuccess = {
                    // Cuando el inicio de sesión sea correcto, aquí iremos a la pantalla de la lista
                    // Aqui dentro va el codigo que llamara a la funcion pantalla princicpal de la app
                }
            )
        }
    }
}