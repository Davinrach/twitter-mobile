package com.example.loginx

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginX(navController: NavController) {
    val torsos = FontFamily(Font(R.font.torsos))
    val awan = FontFamily(Font(R.font.awan))
    var showDialog by remember { mutableStateOf(false) }

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
            text = "See what's\nhappening in the\nworld right now.",
            style = TextStyle(
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = torsos
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 300.dp, start = 50.dp)
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
                .align(Alignment.TopCenter)
                .padding(top = 550.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Logo",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    text = "Continue with Google",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = awan
                    )
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                containerColor = colorResource(id = R.color.dark_grey),
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_x),
                            contentDescription = "Logo X",
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = "Choose an account",
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "to continue to X",
                            color = colorResource(id = R.color.dark_grey2),
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(end = 20.dp)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(-10.dp)
                            ) {
                                Text(
                                    text = "Nur Cahyo",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "ncahyooo69@gmail.com",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.dark_grey)
                        )
                    ) {}
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 653.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                color = Color.Gray,
                modifier = Modifier.width(120.dp).height(1.dp)
            )
            Text(
                text = "or",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 12.sp,
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier.width(120.dp).height(1.dp)
            )
        }

        Button(
            onClick = { navController.navigate("signup") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
                .align(Alignment.TopCenter)
                .padding(top = 622.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Create account",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontFamily = awan
                )
            )
        }

        Text(
            text = buildAnnotatedString {
                append("By signing up, you agree to our ")
                pushStyle(SpanStyle(color = colorResource(id = R.color.birux)))
                append("Terms")
                pop()
                append(", ")
                pushStyle(SpanStyle(color = colorResource(id = R.color.birux)))
                append("Privacy Policy")
                pop()
                append(",\nand ")
                pushStyle(SpanStyle(color = colorResource(id = R.color.birux)))
                append("Cookie Use")
                pop()
            },
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 160.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        val annotatedText = buildAnnotatedString {
            append("Have an account already? ")
            pushStringAnnotation(tag = "login", annotation = "login")
            withStyle(style = SpanStyle(color = colorResource(id = R.color.birux))) {
                append("Log in")
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Left
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start = 65.dp, bottom = 80.dp),
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "login", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate("login_screen") // ✅ navigasi diperbaiki
                    }
            }
        )
    }

}
