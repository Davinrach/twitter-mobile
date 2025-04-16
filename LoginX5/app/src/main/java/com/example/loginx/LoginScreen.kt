package com.example.loginx

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val inputText = remember { mutableStateOf("") }
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val savedEmail = sharedPrefManager.getEmail()
    val savedUsername = sharedPrefManager.getName()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart).padding(top = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logo_x),
            contentDescription = "Logo X",
            modifier = Modifier
                .size(65.dp)
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 112.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "To get started, first enter your phone, email, or\n@username",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = inputText.value,
                    onValueChange = { inputText.value = it },
                    label = { Text("Phone, email, or username") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1DA1F2),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color(0xFF1DA1F2),
                        focusedLabelColor = Color(0xFF1DA1F2),
                        unfocusedLabelColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "Forgot password clicked", Toast.LENGTH_SHORT).show()
                    },
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                        .padding(bottom = 23.dp)
                ) {
                    Text("Forgot password?", color = Color.White)
                }

                Button(
                    onClick = {
                        if (inputText.value == savedEmail || inputText.value == savedUsername) {
                            Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show()
                            navController.navigate("profile_screen")  // Perbaiki rute ke profile_screen
                        } else {
                            Toast.makeText(context, "Username or email is incorrect", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    enabled = inputText.value.isNotEmpty(),
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Next")
                }
            }
        }
    }
}
