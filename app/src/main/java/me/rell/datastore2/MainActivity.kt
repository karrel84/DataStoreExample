package me.rell.datastore2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import me.rell.datastore2.preferences.DarkModeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        darkMode.setOnClickListener {
            startActivity(Intent(this, DarkModeActivity::class.java))
        }
    }
}