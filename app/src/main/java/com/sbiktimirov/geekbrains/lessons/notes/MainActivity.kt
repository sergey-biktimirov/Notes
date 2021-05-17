package com.sbiktimirov.geekbrains.lessons.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sbiktimirov.geekbrains.lessons.notes.fragment.AppInfoFragment
import com.sbiktimirov.geekbrains.lessons.notes.fragment.NoteListFragment

const val NAV_HOME_FRAGMENT = R.layout.fragment_note_list
const val NAV_INFO_FRAGMENT = R.layout.fragment_app_info

class MainActivity : AppCompatActivity() {
    private var currentFragment: Fragment = NoteListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavDrawer(initToolBar())
    }

    private fun navTo(rId: Int) {
        when (rId) {
            NAV_HOME_FRAGMENT -> {
                currentFragment = NoteListFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.note_list_container, currentFragment)
                    .commit()
            }
            NAV_INFO_FRAGMENT -> {
                currentFragment = AppInfoFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.note_list_container, AppInfoFragment())
                    .commit()
            }
            else -> {
                currentFragment = NoteListFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.note_list_container, currentFragment)
                    .commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navTo(currentFragment.id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initToolBar(): Toolbar {
        val toolbar: Toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        return toolbar
    }

    private fun initNavDrawer(toolbar: Toolbar) {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)

        toggle.syncState()

        val closeDrawer = {
            drawer.closeDrawer(GravityCompat.START)
        }

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    navTo(NAV_HOME_FRAGMENT)
                    closeDrawer()
                    true
                }
                R.id.action_info -> {
                    navTo(NAV_INFO_FRAGMENT)
                    closeDrawer()
                    true
                }
                else -> {
                    navTo(NAV_HOME_FRAGMENT)
                    closeDrawer()
                    true
                }
            }
        }
    }
}