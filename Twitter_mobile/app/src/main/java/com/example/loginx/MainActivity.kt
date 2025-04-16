package com.example.loginx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavHost() // Gunakan ini, bukan AppNavigator()
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginX(navController) }
        composable("signup") { SignUpScreen(navController) }

        // HomeScreen tetap disiapkan, meskipun tidak digunakan langsung sekarang
        composable("home") { HomeScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to Home!",
            color = Color.White,
            fontSize = 24.sp
        )
    }
}

@Composable
fun TestDatabaseConnection() {
    val scope = rememberCoroutineScope()
    var isConnected by remember { mutableStateOf<Boolean?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            scope.launch {
                // Ganti logika koneksi ke API
                isConnected = checkApiConnection()
            }
        }) {
            Text("Cek Koneksi")
        }

        Spacer(modifier = Modifier.height(16.dp))
        when (isConnected) {
            true -> Text("✅ Terkoneksi ke API", color = Color.Green)
            false -> Text("❌ Gagal konek API", color = Color.Red)
            null -> Text("Klik tombol untuk cek koneksi", color = Color.Gray)
        }
    }
}

// Fungsi untuk cek koneksi API (Contoh)
suspend fun checkApiConnection(): Boolean {
    // Ganti dengan logika untuk mengecek koneksi API
    return true // Contoh, anggap API terkoneksi
}
