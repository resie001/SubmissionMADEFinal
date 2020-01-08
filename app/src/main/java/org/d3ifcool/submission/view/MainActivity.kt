package org.d3ifcool.submission.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.d3ifcool.submission.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView : BottomNavigationView = findViewById(R.id.bottom_navigation)
        val bottomNavController =findNavController(R.id.nav_host_fragment)
        val appBarConf = AppBarConfiguration(
            setOf(
                R.id.navigation_movie, R.id.navigation_show, R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(bottomNavController, appBarConf)
        bottomNavView.setupWithNavController(bottomNavController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun openSettingLanguage(item: MenuItem){
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }
}
