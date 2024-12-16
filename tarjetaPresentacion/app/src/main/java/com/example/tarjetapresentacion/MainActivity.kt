package com.example.tarjetapresentacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Vertical
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarjetapresentacion.ui.theme.TarjetaPresentacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarjetaPresentacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFDDE6E0)) // Fondo general
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = Modifier.weight(1f))
        Encabezado()
        Spacer(modifier = Modifier.weight(0.8f))
        InformacionContacto()
        Spacer(modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun Encabezado() {
    val image = painterResource(R.drawable.android_logo)
    Column(

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .background(Color(0xFF0F172A)) // Fondo oscuro detrás de la imagen
                    .padding(16.dp)
                    .height(120.dp)
                    .width(120.dp),
            )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Diego Muñoz",
            fontSize = 42.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Desarrollador Android",
            fontSize = 16.sp,
            color = Color(0xFF064E3B),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InformacionContacto() {
    Column{
        ContactoItem(icon = Icons.Default.Phone, text = "+34 123 456 789")
        Spacer(modifier = Modifier.height(8.dp))
        ContactoItem(icon = Icons.Default.Share, text = "@AndroidDev")
        Spacer(modifier = Modifier.height(8.dp))
        ContactoItem(icon = Icons.Default.Email, text = "diego@example.com")
    }
}

@Composable
fun ContactoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row{
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF064E3B),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 14.sp,

        )
    }
}

@Preview(showBackground = true , showSystemUi = true,)
@Composable
fun GreetingPreview() {
    TarjetaPresentacionTheme {
        Greeting()
    }
}