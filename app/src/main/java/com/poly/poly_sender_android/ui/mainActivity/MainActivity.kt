package com.poly.poly_sender_android.ui.mainActivity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
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
import com.poly.poly_sender_android.*
import com.poly.poly_sender_android.databinding.ActivityMainBinding
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationSection.CreationSectionFragmentDirections
import com.poly.poly_sender_android.ui.auth.login.LoginFragmentDirections
import com.poly.poly_sender_android.ui.filters.creationFilter.CreationFilterFragmentDirections
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
            val layoutParams =
                binding.bottomNavigation.layoutParams as CoordinatorLayout.LayoutParams
            val bottomViewNavigationBehavior =
                layoutParams.behavior as HideBottomViewOnScrollBehavior
            bottomViewNavigationBehavior.slideUp(binding.bottomNavigation)

            val searchViewMenuItem = binding.toolbar.menu.findItem(R.id.action_search)
            val searchView = searchViewMenuItem?.actionView as? SearchView
            searchView?.setQuery("",  true)
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
            clicked = appAnimations.onExpandedFloatingButtonClicked(
                true,
                binding.floatingButton,
                binding.floatingButtonAddAttribute,
                binding.floatingButtonAddSection,
                binding.floatingButtonAddFilter
            )
            navController.navigate(CreationSectionFragmentDirections.actionGlobalCreationSectionFragment())
        }

        binding.floatingButtonAddAttribute.setOnClickListener {
            clicked = appAnimations.onExpandedFloatingButtonClicked(
                true,
                binding.floatingButton,
                binding.floatingButtonAddAttribute,
                binding.floatingButtonAddSection,
                binding.floatingButtonAddFilter
            )
            navController.navigate(CreationAttributeFragmentDirections.actionGlobalCreationAttributeParamFragment())
        }

        binding.floatingButtonAddFilter.setOnClickListener {
            clicked = appAnimations.onExpandedFloatingButtonClicked(
                true,
                binding.floatingButton,
                binding.floatingButtonAddAttribute,
                binding.floatingButtonAddSection,
                binding.floatingButtonAddFilter
            )
            navController.navigate(CreationFilterFragmentDirections.actionGlobalCreationFilterParamFragment())
        }

        App.mCurrentActivity = this
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val searchViewMenuItem = menu?.findItem(R.id.action_search)
        val searchView = searchViewMenuItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return false
            }

            fun callSearch(query: String) {
                activityViewModel.searchQueryStateFlow.value = query
            }

        })

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(App.appBar.res, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_attributing -> {
                activityViewModel.triggerAttributingEvent(true)
                true
            }
            R.id.action_select_all -> {
                activityViewModel.triggerSelectAllEvent(true)
                true
            }
            R.id.action_apply -> {
                activityViewModel.triggerApply(true)
                true
            }
            R.id.action_clear -> {
                activityViewModel.triggerClear(true)
                true
            }
            R.id.action_next -> {
                activityViewModel.triggerNext(true)
                true
            }
            R.id.action_edit -> {
                activityViewModel.triggerEdit(true)
                true
            }
            R.id.action_share -> {
                activityViewModel.triggerShare(true)
                true
            }
            R.id.action_delete -> {
                activityViewModel.triggerDelete(true)
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