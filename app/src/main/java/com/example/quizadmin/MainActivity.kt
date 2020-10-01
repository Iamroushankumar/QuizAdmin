package com.example.quizadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.quizadmin.addquestion.AddQuestionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private var currentFragmentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.frameContainer)
        if (hostFragment is NavHostFragment) {
            navHostFragment = hostFragment
            navController = hostFragment.navController
        }
        setUpBottomNavigation(navController)

        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                currentFragmentId = destination.id
            }

        })


    }

    private fun setUpBottomNavigation(navcontroller: NavController) {
        NavigationUI.setupWithNavController(bnavigation, navController)
        bnavigation.setOnNavigationItemReselectedListener {
            val hostFragment = supportFragmentManager.findFragmentById(R.id.frameContainer)
            if (hostFragment is NavHostFragment) {
                val currentFragment = hostFragment.childFragmentManager.fragments.first()
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
    }
}