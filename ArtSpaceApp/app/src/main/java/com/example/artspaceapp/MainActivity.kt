package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

// Define a data class for an Artwork
data class Artwork(
    val imageResId: Int,
    val title: String,
    val artist: String,
    val year: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

// Set status bar color to white programmatically
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true // Dark status bar icons
            window.statusBarColor = Color.White.toArgb() // Set status bar color to white
        }

        setContent {
            ArtSpaceAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    // List of artworks
    val artworks = listOf(
        Artwork(R.drawable.artwork_1, "Artwork 1", "Artist 1", "2020"),
        Artwork(R.drawable.artwork_2, "Artwork 2", "Artist 2", "2019"),
        Artwork(R.drawable.artwork_3, "Artwork 3", "Artist 3", "2021")
    )

    // State to track the current artwork index
    var currentArtworkIndex by remember { mutableStateOf(0) }

    // Get the current artwork based on the index
    val currentArtwork = artworks[currentArtworkIndex]

    val customBlue = Color(0xFF6082B6)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(White) // Set background color to white
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Artwork Wall inside a Box with border and shadow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center // Center the content
        ) {
            // Box to provide shadow and border around the image
            Surface(
                modifier = Modifier
                    .padding(32.dp)
                    .border(
                        8.dp,
                        White,
                        RoundedCornerShape(16.dp)
                    )  // White border around the image
                    .shadow(8.dp, RoundedCornerShape(16.dp)),  // Shadow outside the white border
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = painterResource(id = currentArtwork.imageResId),
                    contentDescription = "Artwork",
                    modifier = Modifier
                        .size(300.dp) // Set the image size
                        .clip(RoundedCornerShape(16.dp))  // Clip the image to match the border's shape
                        .padding(4.dp),  // Padding inside the border for aesthetics
                    contentScale = ContentScale.Inside  // Scale the image to fit inside the box
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Artwork Descriptor
        Surface(
            color = Color(0xFFE0E0E0), // Light gray background for the descriptor
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.widthIn(max = 310.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentArtwork.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${currentArtwork.artist} (${currentArtwork.year})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Controller
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),  // Padding from the bottom
            horizontalArrangement = Arrangement.SpaceBetween // Center the buttons horizontally
        ) {
            Button(
                onClick = {
                    // Show the previous artwork
                    currentArtworkIndex = if (currentArtworkIndex == 0) {
                        artworks.size - 1 // Wrap to the last artwork
                    } else {
                        currentArtworkIndex - 1
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = customBlue), // Set button color
                modifier = Modifier
                    .width(120.dp) // Set button width
                    .height(40.dp) // Set button height
                    .padding(end = 6.dp) // Add more spacing between buttons
            ) {
                Text(text = "Previous", color = Color.White) // Set text color
            }

            Button(
                onClick = {
                    // Show the next artwork
                    currentArtworkIndex = if (currentArtworkIndex == artworks.size - 1) {
                        0 // Wrap to the first artwork
                    } else {
                        currentArtworkIndex + 1
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = customBlue), // Set button color
                modifier = Modifier
                    .width(120.dp) // Set button width
                    .height(40.dp) // Set button height
                    .padding(start = 6.dp) // Add more spacing between buttons
            ) {
                Text(text = "Next", color = Color.White) // Set text color
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceAppTheme {
        MainScreen()
    }
}

