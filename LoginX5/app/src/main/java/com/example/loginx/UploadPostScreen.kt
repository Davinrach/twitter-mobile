package com.example.loginx

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun UploadPostScreen(
    navController: NavHostController,
    viewModel: PostViewModel // jika belum dipakai, bisa hapus dulu
) {
    var postText by remember { mutableStateOf("") }
    var isPostUploaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "Create a new post",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        BasicTextField(
            value = postText,
            onValueChange = { postText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
                .padding(12.dp),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (postText.isNotBlank()) {
                    val postRequest = PostRequest(user_id = 1, content = postText)

                    // Gunakan viewModelScope.launch untuk memanggil fungsi suspend
                    viewModel.viewModelScope.launch {
                        try {
                            // Memanggil fungsi suspend uploadPost di dalam coroutine
                            viewModel.addPost(postText)  // ini penting
                            isPostUploaded = true
                            navController.popBackStack()
                        } catch (e: HttpException) {
                            // Tangani jika terjadi error HTTP
                        } catch (e: Exception) {
                            // Tangani error lainnya
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2)),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Upload", color = Color.White)
        }

        if (isPostUploaded) {
            Text("Post uploaded successfully!", color = Color.Green)
        }
    }
}
