package com.example.guide

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guide2.Video

class MainActivity : AppCompatActivity() {

    private var currentImg = 0
    private lateinit var image: ImageView
    var places = arrayOf(
        "Somnath", "Mallikārjuna", "Mahakaleshwar", "Omkareshwar", "Kedarnath", "Bhimashankar",
        "Kashi Vishwanath", "Trimbakeshwar", "Nageshwar", "Baidyanath", "Rameshweram", "Grishneshwar"
    )
    private val videoLinks = arrayOf(
        "https://www.youtube.com/watch?v=57TpFmz4yHs", // Somnath
        "https://youtu.be/vGegd4Lsfqw?si=bRcEeUm_59yMG3wl", // Mallikārjuna
        "https://www.youtube.com/watch?v=IJZjnWXQnXc", // Mahakaleshwar
        "https://www.youtube.com/watch?v=sjCPaXrczIE&list=PLozegpsvL08By9kuWr--4jk_U1DJylR4M&index=3", // Omkareshwar
        "https://youtu.be/ldg_Q-cVs84?si=PpSgVMJgh-jldE4A", // Kedarnath
        "https://youtu.be/rNWuBEjyCz0?si=1JRYZ9qOYqJ0N_d9", // Bhimashankar
        "https://www.youtube.com/watch?v=0U7FSi1tHTU&list=PLozegpsvL08By9kuWr--4jk_U1DJylR4M&index=5", // Kashi Vishwanath
        "https://youtu.be/CKQY0D4Hz9Q?si=tVNn3Ys-6F5fD02y", // Trimbakeshwar
        "https://youtu.be/KeLs5FW7TMk?si=fFIN1mF9bdCFL4Iy", // Nageshwar
        "https://youtu.be/mnvmq_BLTs4?si=it6FJNAfwuR3562p", // Baidyanath
        "https://youtu.be/dN6L8we3RNs?si=vQziuDfxj-yVeJxn", // Rameshweram
        "https://youtu.be/kZT9qArzgzw?si=b_p55sWoFcARLuqx"  // Grishneshwar
    )

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prev = findViewById<ImageButton>(R.id.btnprev)
        val next = findViewById<ImageButton>(R.id.btnnext)
        val placeName = findViewById<TextView>(R.id.textView2)
        val aiGuideButton = findViewById<Button>(R.id.aiGuideButton)

        // Initialize MediaPlayer and play song at 30% volume
        mediaPlayer = MediaPlayer.create(this, R.raw.song) // Replace with your song
        mediaPlayer.isLooping = true
        val volume = 0.3f
        mediaPlayer.setVolume(volume, volume)
        mediaPlayer.start()

        // AI Guide button click listener
        aiGuideButton.setOnClickListener {
            playVirtualTourVideo(places[currentImg]) // Pass the current place name
        }

        next.setOnClickListener {
            changeImage(true)
        }

        prev.setOnClickListener {
            changeImage(false)
        }

        // Change image after 40 seconds
        val handler = Handler()
        handler.postDelayed({
            changeImage(true)
        }, 40000) // 40 seconds delay
    }

    private fun changeImage(isNext: Boolean) {
        var idCurrentImageString = "pic$currentImg"
        var idCurrentImageInt = this.resources.getIdentifier(idCurrentImageString, "id", packageName)
        image = findViewById(idCurrentImageInt)
        image.alpha = 0f

        currentImg = if (isNext) (12 + currentImg + 1) % 12 else (12 + currentImg - 1) % 12
        var idImageToShowString = "pic$currentImg"
        var idImageToShowInt = this.resources.getIdentifier(idImageToShowString, "id", packageName)
        image = findViewById(idImageToShowInt)
        image.alpha = 1f

        findViewById<TextView>(R.id.textView2).text = places[currentImg]
    }

    fun playVirtualTourVideo(placeName: String) {
        val placeIndex = places.indexOf(placeName)

        if (placeIndex >= 0 && placeIndex < videoLinks.size) {
            val videoUrl = videoLinks[placeIndex]

            Toast.makeText(this, "Playing virtual tour for $placeName", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Video::class.java).apply {
                putExtra("VIDEO_URL", videoUrl)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Video not available for this place.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause() // Pause the song when the activity is not visible
    }

    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start() // Resume playing the song when the activity is resumed
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Release resources when activity is destroyed
    }
}
