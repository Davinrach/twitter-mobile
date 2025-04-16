package com.example.loginx

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.Response

// Request body
data class PostRequest(val user_id: Int, val content: String)
data class DeleteByIdRequest(val post_id: Int)

// Response item
data class Post(val id: Int, val content: String)

interface ApiService {
    @POST("/add_post")
    suspend fun uploadPost(@Body post: PostRequest): Response<Void>

    @POST("/delete_post_by_id")
    suspend fun deletePostById(@Body request: DeleteByIdRequest): Response<Void>

    @GET("/posts/{user_id}")
    suspend fun getPosts(@Path("user_id") userId: Int): Response<List<Post>>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

class PostViewModel : ViewModel() {
    val posts = mutableStateListOf<Post>()

    fun loadPosts(userId: Int = 1) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getPosts(userId)
                if (response.isSuccessful) {
                    posts.clear()
                    posts.addAll(response.body() ?: emptyList())
                    Log.d("PostViewModel", "Loaded posts: ${posts.size}")
                } else {
                    Log.e("PostViewModel", "Failed to load posts: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error loading posts: ${e.message}")
            }
        }
    }

    fun addPost(content: String) {
        viewModelScope.launch {
            try {
                val postRequest = PostRequest(user_id = 1, content = content)
                val response = RetrofitClient.apiService.uploadPost(postRequest)
                if (response.isSuccessful) {
                    loadPosts() // refresh list setelah tambah
                } else {
                    Log.e("PostViewModel", "Failed to upload post: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error uploading post: ${e.message}")
            }
        }
    }

    fun removePostById(postId: Int) {
        Log.d("DeletePost", "Mengirim permintaan hapus post id=$postId")

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deletePostById(DeleteByIdRequest(post_id = postId))
                if (response.isSuccessful) {
                    posts.removeAll { it.id == postId }
                    Log.d("DeletePost", "Post berhasil dihapus dari server dan UI")
                } else {
                    Log.e("DeletePost", "Gagal hapus post: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("DeletePost", "Error saat hapus: ${e.message}")
            }
        }
    }
}
