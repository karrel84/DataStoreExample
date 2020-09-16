package me.rell.datastore2.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_dark_mode.*
import kotlinx.coroutines.launch
import me.rell.datastore2.R

class DarkModeActivity : AppCompatActivity() {
    private lateinit var settingManager: SettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        settingManager = SettingManager(applicationContext)

        observeUiDarkMode()
        initButtons()
    }

    private fun initButtons() {
        darkMode.setOnClickListener {
            lifecycleScope.launch {
                settingManager.setUiMode(UiMode.DARK)
            }
        }

        lightMode.setOnClickListener {
            lifecycleScope.launch {
                settingManager.setUiMode(UiMode.LIGHT)
            }
        }
    }

    private fun observeUiDarkMode() {
        settingManager.uiModeFlow.asLiveData().observe(this) {uiMode ->
            when (uiMode) {
                UiMode.LIGHT -> onLightMode()
                UiMode.DARK  -> onDarkMode()
            }
        }
    }


    private fun onLightMode() {
        image.setImageResource(R.drawable.light)
    }

    private fun onDarkMode() {
        image.setImageResource(R.drawable.dark)
    }
}