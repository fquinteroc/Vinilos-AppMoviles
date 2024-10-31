package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.databinding.ActivityMainBinding
import com.appsmoviles.grupo15.vinilos_app.ui.screens.RoleSelectionScreen
import com.appsmoviles.grupo15.vinilos_app.ui.theme.Vinilos_AppTheme
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private var userRole: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        drawerLayout = binding.drawerLayout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController, drawerLayout)

        val navigationView: NavigationView = binding.navigationView
        NavigationUI.setupWithNavController(navigationView, navController)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, binding.myToolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView: View = binding.navigationView.getHeaderView(0)
        val textViewRoleHeader = headerView.findViewById<TextView>(R.id.textViewRoleHeader)

        val composeView = findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            Vinilos_AppTheme {
                RoleSelectionScreen { role ->
                    userRole = role
                    updateNavHeaderRole(textViewRoleHeader, role)
                    composeView.visibility = View.GONE
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    composeView.visibility = View.VISIBLE
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.albumFragment -> {
                    navController.navigate(R.id.albumFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    private fun updateNavHeaderRole(textView: TextView, role: String) {
        textView.text = "Vinilos - $role"
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }
}
