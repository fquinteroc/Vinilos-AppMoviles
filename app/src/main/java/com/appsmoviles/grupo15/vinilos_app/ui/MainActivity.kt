package com.appsmoviles.grupo15.vinilos_app.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var textViewRoleHeader: TextView
    private lateinit var toggle: ActionBarDrawerToggle

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

        toggle = ActionBarDrawerToggle(this, drawerLayout, binding.myToolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView: View = binding.navigationView.getHeaderView(0)
        textViewRoleHeader = headerView.findViewById(R.id.textViewRoleHeader)
        updateRoleHeader()

        // Manejar la navegación del BottomNavigationView
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_album -> {
                    navController.navigate(R.id.albumFragment)
                    true
                }
                R.id.bottom_nav_artist -> {
                    navController.navigate(R.id.artistFragment)
                    true
                }
                R.id.bottom_nav_collector -> {
                    navController.navigate(R.id.collectorFragment)
                    true
                }
                R.id.bottom_nav_logout -> {
                    navController.navigate(R.id.action_global_roleSelectionFragment)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.roleSelectionFragment) {
                binding.bottomNavigation.visibility = View.GONE
                supportActionBar?.hide()
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
                supportActionBar?.show()
            }

            if (destination.id in listOf(R.id.albumDetailFragment, R.id.artistDetailFragment)) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toggle.isDrawerIndicatorEnabled = false
                toggle.setToolbarNavigationClickListener {
                    navController.navigateUp()
                }
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toggle.isDrawerIndicatorEnabled = true
                toggle.toolbarNavigationClickListener = null
            }
            toggle.syncState()
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
                    navController.navigate(R.id.action_global_roleSelectionFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.albumFragment -> {
                    navController.navigate(R.id.albumFragment)
                    binding.bottomNavigation.selectedItemId = R.id.bottom_nav_album
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.artistFragment -> {
                    navController.navigate(R.id.artistFragment)
                    binding.bottomNavigation.selectedItemId = R.id.bottom_nav_artist
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.collectorFragment -> {
                    navController.navigate(R.id.collectorFragment)
                    binding.bottomNavigation.selectedItemId = R.id.bottom_nav_collector
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    private fun updateRoleHeader() {
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val selectedRole = sharedPref.getString("selected_role", "Usuario")
        textViewRoleHeader.text = "Vinilos - $selectedRole"
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (navController.currentDestination?.id in listOf(R.id.albumDetailFragment, R.id.artistDetailFragment)) {
            navController.navigateUp()
        } else {
            NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
        }
    }
}