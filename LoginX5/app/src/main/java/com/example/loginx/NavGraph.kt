package com.example.loginx

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginx.ProfileX
import com.example.loginx.UploadPostScreen
import com.example.loginx.LoginX
import com.example.loginx.SignUpScreen
import com.example.loginx.HomeScreen
import com.example.loginx.LoginScreen
import com.example.loginx.SharedPrefManager
import com.example.loginx.PostViewModel

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val postViewModel: PostViewModel = viewModel() // 🔥 ViewModel dibuat satu di sini

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginX(navController)
        }
        composable("signup") {
            SignUpScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("upload_post_screen") {
            UploadPostScreen(navController, postViewModel) // 👈 Pakai instance yang sama
        }
        composable("profile_screen") {
            val name = sharedPrefManager.getName() ?: "User"
            val dob = sharedPrefManager.getDob() ?: "Unknown"
            ProfileX(navController = navController, name = name, dob = dob, viewModel = postViewModel) // 👈 Pakai instance yang sama
        }
    }
}

