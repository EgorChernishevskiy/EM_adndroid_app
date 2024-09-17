package com.example.em_test_task.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.auth.presentation.fragments.EMAIL_KEY
import com.example.auth.presentation.fragments.LoginFragment
import com.example.auth.presentation.fragments.PinFragment
import com.example.auth.presentation.navigation.AuthNavigation
import com.example.core.presentation.models.VacancyUI
import com.example.core.presentation.navigation.Navigation
import com.example.em_test_task.R
import com.example.main.presentation.fragments.MainFragment
import com.example.vacancydetails.presentation.fragments.VACANCY_KEY
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), AuthNavigation, Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                //replace(R.id.fragmentContainer, LoginFragment())
                replace(R.id.fragmentContainer, MainFragment())
            }
            //findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
        }


        supportFragmentManager.addOnBackStackChangedListener {
            toggleBottomNavigationVisibility()
        }
    }

    override fun navigateToPin(email: String) {
        val pinFragment = PinFragment().apply {
            arguments = Bundle().apply {
                putString(EMAIL_KEY, email)
            }
        }

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, pinFragment)
            addToBackStack(null)
        }
    }

    override fun closeAuth() {
        supportFragmentManager.popBackStack(
            null,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, MainFragment())
        }
    }

    private fun toggleBottomNavigationVisibility() {
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment is LoginFragment || currentFragment is PinFragment) {
            navView.visibility = View.GONE
        } else {
            navView.visibility = View.VISIBLE
        }
    }

    override fun navigateToVacancy(vacancy: VacancyUI) {
        val navController =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                ?.findNavController()
        navController?.navigate(R.id.to_vacancyFragment, bundleOf(VACANCY_KEY to vacancy))
    }

        override fun navigateBack() {
        findNavController(R.id.fragmentContainer).popBackStack()
    }

}