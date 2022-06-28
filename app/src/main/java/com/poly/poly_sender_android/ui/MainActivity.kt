package com.poly.poly_sender_android.ui

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.databinding.ActivityMainBinding
import com.poly.poly_sender_android.util.AppAnimations
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val activityViewModel: MainActivityViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var timerInProgress = false
    private var timer = object : CountDownTimer(3000, 1) {
        override fun onTick(p0: Long) {

        }

        override fun onFinish() {
            timerInProgress = false
        }

    }
    private var clicked = false
    private lateinit var appAnimations: AppAnimations

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appAnimations = AppAnimations(applicationContext)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomNavView: BottomNavigationView = binding.bottomNavigation
        navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val layoutParams = binding.bottomNavigation.layoutParams as CoordinatorLayout.LayoutParams
            val bottomViewNavigationBehavior = layoutParams.behavior as HideBottomViewOnScrollBehavior
            bottomViewNavigationBehavior.slideUp(binding.bottomNavigation)
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.StudentsFragment,
                R.id.AttributesFragment,
                R.id.FiltersFragment
            ), drawerLayout
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavView.setupWithNavController(navController)

        binding.floatingButton.setOnClickListener {
            clicked = appAnimations.onExpandedFloatingButtonClicked(
                clicked,
                binding.floatingButton,
                binding.floatingButtonAddAttribute,
                binding.floatingButtonAddSection,
                binding.floatingButtonAddFilter
            )
        }

        binding.floatingButtonAddSection.setOnClickListener {

        }

        binding.floatingButtonAddAttribute.setOnClickListener {

        }

        App.mCurrentActivity = this
    }

    override fun onStop() {
        super.onStop()

        binding.floatingButtonAddFilter.visibility = View.INVISIBLE
        binding.floatingButtonAddAttribute.visibility = View.INVISIBLE
        binding.floatingButtonAddSection.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        ViewCompat.setOnApplyWindowInsetsListener(binding.floatingButton) { view, insets ->
            val h = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    view.rootWindowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    view.rootWindowInsets?.stableInsetTop ?: 0
                }
                else -> {
                    0
                }
            }
            binding.floatingButton.updatePadding(bottom = h)
            insets
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val searchViewMenuItem = menu?.findItem(R.id.action_search)
        val searchView = searchViewMenuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return false
            }

            fun callSearch(query: String?) {
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
            }

        })

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (App.test) {
            menuInflater.inflate(R.menu.menu_main, menu)
        } else {
            menuInflater.inflate(R.menu.menu_main_selected, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_attributing -> {
                activityViewModel.setStudentsAttributingEvent(true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        when {
            navController.backQueue.size == 3 && !timerInProgress -> {
                timer.start()
                timerInProgress = true
                Toast.makeText(
                    applicationContext,
                    "Вы уверены, что хотите выйти?",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}