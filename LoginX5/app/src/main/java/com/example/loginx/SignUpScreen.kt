package com.example.loginx

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.navigation.NavController

@Composable
fun SignUpScreen(navController: NavController) {
    val torsos = FontFamily(Font(R.font.torsos))
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    var isNameFocused by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isDobFocused by remember { mutableStateOf(false) }

    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val dobFocusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_x),
            contentDescription = "Logo X",
            modifier = Modifier
                .size(65.dp)
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        )

        Text(
            text = "Halaman Buat Akun",
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = torsos
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 50.dp, top = 100.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            label = {
                Text("Name", color = if (isNameFocused) colorResource(id = R.color.birux) else Color.Gray)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 300.dp)
                .offset(x = 10.dp, y = 10.dp)
                .fillMaxWidth(0.84f)
                .focusRequester(nameFocusRequester)
                .onFocusChanged { isNameFocused = it.isFocused },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.birux),
                unfocusedBorderColor = Color.Gray,
                cursorColor = colorResource(id = R.color.birux)
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            label = {
                Text("Email", color = if (isEmailFocused) colorResource(id = R.color.birux) else Color.Gray)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 380.dp)
                .offset(x = 10.dp, y = 10.dp)
                .fillMaxWidth(0.84f)
                .focusRequester(emailFocusRequester)
                .onFocusChanged { isEmailFocused = it.isFocused },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.birux),
                unfocusedBorderColor = Color.Gray,
                cursorColor = colorResource(id = R.color.birux)
            )
        )

        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            label = {
                Text("Date of Birth", color = if (isDobFocused) colorResource(id = R.color.birux) else Color.Gray)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 460.dp)
                .offset(x = 10.dp, y = 10.dp)
                .fillMaxWidth(0.84f)
                .focusRequester(dobFocusRequester)
                .onFocusChanged { isDobFocused = it.isFocused },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.birux),
                unfocusedBorderColor = Color.Gray,
                cursorColor = colorResource(id = R.color.birux)
            )
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 110.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                sharedPrefManager.saveUserSession(name, email, dateOfBirth)
                Toast.makeText(context, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show()
                navController.navigate("login_screen")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp)
                .padding(start = 300.dp)
        ) {
            Text(
                text = "Create",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            modifier = Modifier
                .size(50.dp)
                .padding(start = 20.dp, top = 30.dp)
                .align(Alignment.TopStart)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}
