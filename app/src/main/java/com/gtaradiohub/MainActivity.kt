package com.gtaradiohub

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import android.media.MediaPlayer

class MainActivity : Activity() {
    private val games = listOf("VICE CITY", "SAN ANDREAS", "GTA III", "GTA IV", "GTA V")
    private val stations = listOf("FLASH FM", "V-ROCK", "WAVE 103", "EMOTION 98.3", "FEVER 105")
    private var gameIndex = 0
    private var stationIndex = 0
    private var player: MediaPlayer? = null
    private lateinit var root: LinearLayout
    private lateinit var gameLabel: TextView
    private lateinit var stationLabel: TextView
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG immersiveSticky)
        buildUi()
    }

    private fun buildUi() {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(Color.rgb(9, 6, 17))
            setPadding(18, 12, 18, 12)
        }
        val sidebar = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(12, 8, 12, 8)
            setBackgroundColor(Color.rgb(20, 13, 31))
        }
        val brand = text("GTA RADIO HUB", 25, "#FF35C9", true)
        sidebar.addView(brand)
        gameLabel = text("", 22, "#FFFFFF", true)
        sidebar.addView(gameLabel)
        val gameHint = text("◀   SWIPE GAME   ▶", 12, "#A99AB8", false)
        sidebar.addView(gameHint)
        root.addView(sidebar, LinearLayout.LayoutParams(0, -1, 1.1f))

        val main = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 8, 24, 8)
        }
        val title = text("RADIO TUNING", 16, "#A99AB8", true)
        main.addView(title)
        stationLabel = text("", 42, "#FF35C9", true)
        main.addView(stationLabel)
        val song = text("YOUR MUSIC • LOCAL PLAYLIST", 18, "#FFFFFF", false)
        main.addView(song)
        status = text("READY TO TUNE", 13, "#40DDF5", false)
        main.addView(status)
        val controls = LinearLayout(this).apply { gravity = Gravity.CENTER; orientation = LinearLayout.HORIZONTAL }
        controls.addView(button("⟲", { prevStation() }))
        controls.addView(button("▶ / II", { status.text = "Add audio files to start playback" }))
        controls.addView(button("⟳", { nextStation() }))
        main.addView(controls)
        main.addView(text("Swipe left/right to change station • Settings: customize playlists", 12, "#8C8298", false))
        root.addView(main, LinearLayout.LayoutParams(0, -1, 2.5f))
        setContentView(root)
        refresh()
        attachSwipe(root)
    }

    private fun text(value: String, size: Int, color: String, bold: Boolean): TextView =
        TextView(this).apply {
            text = value; textSize = size.toFloat(); setTextColor(Color.parseColor(color))
            gravity = Gravity.CENTER; if (bold) setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(8, 10, 8, 10)
        }

    private fun button(label: String, action: () -> Unit): Button =
        Button(this).apply {
            text = label; textSize = 18f; setTextColor(Color.WHITE)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(85, 25, 105)))
            setOnClickListener { action() }
        }

    private fun refresh() {
        gameLabel.text = games[gameIndex]
        stationLabel.text = stations[stationIndex]
        status.text = "${games[gameIndex]} • ${stations[stationIndex]} • TUNED"
    }

    private fun nextStation() { stationIndex = (stationIndex + 1) % stations.size; tune(); refresh() }
    private fun prevStation() { stationIndex = (stationIndex - 1 + stations.size) % stations.size; tune(); refresh() }
    private fun nextGame() { gameIndex = (gameIndex + 1) % games.size; stationIndex = 0; refresh() }
    private fun prevGame() { gameIndex = (gameIndex - 1 + games.size) % games.size; stationIndex = 0; refresh() }

    private fun tune() {
        // Prototype tuning hiss; replace with a licensed/owned radio static asset in res/raw.
        status.text = "📻 TUNING…  ░▒▓ STATIC ▓▒░"
        root.postDelayed({ status.text = "TUNED • ${stations[stationIndex]}" }, 650)
    }

    private fun attachSwipe(view: View) {
        var downX = 0f; var downY = 0f
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { downX = event.x; downY = event.y; true }
                MotionEvent.ACTION_UP -> {
                    val dx = event.x - downX; val dy = event.y - downY
                    if (kotlin.math.abs(dx) > 70 && kotlin.math.abs(dx) > kotlin.math.abs(dy)) {
                        if (downX < view.width * 0.32f) { if (dx < 0) nextGame() else prevGame() }
                        else { if (dx < 0) nextStation() else prevStation() }
                        true
                    } else false
                }
                else -> true
            }
        }
    }

    override fun onDestroy() { player?.release(); player = null; super.onDestroy() }
}
