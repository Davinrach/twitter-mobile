package com.example.loginx

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun ProfileX(
    navController: NavHostController,
    name: String,
    dob: String,
    viewModel: PostViewModel
) {
    val tabs = listOf("Posts", "Replies", "Highlights", "Articles", "Media", "Likes")
    var selectedTab by remember { mutableStateOf(tabs.first()) }

    val tabPositions = remember { mutableStateMapOf<String, IntOffset>() }
    val tabWidths = remember { mutableStateMapOf<String, Int>() }

    var rowStartOffset by remember { mutableStateOf(0) }
    var underlineReady by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val animatedOffsetX by animateDpAsState(
        targetValue = with(density) { (tabPositions[selectedTab]?.x ?: 0).toDp() },
        label = "UnderlineOffset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = with(density) { (tabWidths[selectedTab] ?: 0).toDp() },
        label = "UnderlineWidth"
    )

    // 🔄 Load post saat screen dibuka
    LaunchedEffect(Unit) {
        delay(100)
        underlineReady = true
        viewModel.loadPosts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color(0xFF2ECC71))
        ) {
            IconButton(
                onClick = { /* Back */ },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Black)
            }
        }

        Column(modifier = Modifier.offset(y = (-40).dp)) {
            // Profile Info
            Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.Bottom) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    onClick = { /* TODO */ },
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Edit profile", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.Verified, contentDescription = "Verified", tint = Color(0xFF1DA1F2), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Get Verified", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1DA1F2))
            }

            Text("@$name", color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp).padding(top = 30.dp)) {
//                Spacer(modifier = Modifier.width(8.dp))
                Text("📆 Joined Jan 2012", color = Color.Gray, fontSize = 16.sp)
            }

            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Row {
                    Text("101 ", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                    Text("Following", color = Color.Gray, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row {
                    Text("17 ", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                    Text("Followers", color = Color.Gray, fontSize = 16.sp)
                }
            }

            // Tabs + Underline
            Box {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            rowStartOffset = it.positionInWindow().x.toInt()
                        },
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(tabs) { tab ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { selectedTab = tab }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .onGloballyPositioned { coords ->
                                    val x = coords.positionInWindow().x.toInt()
                                    val offsetX = x - rowStartOffset
                                    val width = coords.size.width
                                    tabPositions[tab] = IntOffset(offsetX, 0)
                                    tabWidths[tab] = width
                                }
                        ) {
                            Text(
                                text = tab,
                                color = if (selectedTab == tab) Color.White else Color.Gray,
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                if (underlineReady) {
                    Box(
                        modifier = Modifier
                            .offset(x = animatedOffsetX)
                            .padding(top = 36.dp)
                            .width(animatedWidth)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFF1DA1F2))
                    )
                }
            }

            Divider(color = Color.Gray, thickness = 0.5.dp)

            // Tab Content Animated + Upload Button
            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedContent(targetState = selectedTab, label = "TabContent") { tab ->
                    when (tab) {
                        "Posts" -> {
                            if (viewModel.posts.isEmpty()) {
                                Text(
                                    text = "No posts yet",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(16.dp)
                                )
                            } else {
                                LazyColumn {
                                    items(viewModel.posts) { post ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile),
                                                contentDescription = "Profile Picture",
                                                modifier = Modifier
                                                    .padding(start = 15.dp)
                                                    .size(40.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.Gray),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(12.dp))

                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = name,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                                Text(
                                                    text = post.content,
                                                    color = Color.White,
                                                    fontSize = 16.sp
                                                )
                                            }

                                            IconButton(
                                                onClick = {
                                                    viewModel.removePostById(post.id)
                                                },
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            ) {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete Post", tint = Color.Gray)
                                            }
                                        }

                                        Divider(color = Color.Gray, thickness = 0.5.dp)
                                    }
                                }
                            }
                        }

                        "Replies" -> Column(modifier = Modifier.padding(16.dp)) {
                            Text("You replied to @someone", color = Color.Gray)
                        }

                        "Highlights" -> Column(modifier = Modifier.padding(16.dp)) {
                            Text("No highlights yet", color = Color.Gray)
                        }

                        "Articles" -> Column(modifier = Modifier.padding(16.dp)) {
                            Text("No articles available", color = Color.Gray)
                        }

                        "Media" -> Column(modifier = Modifier.padding(16.dp)) {
                            Text("Media you've shared", color = Color.Gray)
                        }

                        "Likes" -> Column(modifier = Modifier.padding(16.dp)) {
                            Text("You haven't liked any posts yet", color = Color.Gray)
                        }
                    }
                }

                if (selectedTab == "Posts") {
                    FloatingActionButton(
                        onClick = { navController.navigate("upload_post_screen") },
                        containerColor = Color(0xFF1DA1F2),
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(56.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Upload")
                    }
                }
            }
        }
    }
}
