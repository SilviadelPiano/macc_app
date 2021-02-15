package it.sapienza.macc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import it.sapienza.macc.R

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView

    // private lateinit var navListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavDrawer()

    }

    private fun initNavDrawer() {
        navigationController = Navigation.findNavController(findViewById(R.id.fragment))
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navigationController.graph, drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val headerView: View = navigationView.inflateHeaderView(R.layout.drawer_header)
        // val headerImage: CircularImageView = headerView.findViewById(R.id.circularImageId)
        navigationView.setupWithNavController(navigationController)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}