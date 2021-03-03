package it.sapienza.macc.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import it.sapienza.macc.BuildConfig
import it.sapienza.macc.model.Model
import it.sapienza.macc.model.Senator

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView

    // private lateinit var navListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initNavDrawer()
        //performAuth()

    }

    private fun performAuth() {
        val clientId = BuildConfig.CLIENT_ID
        //val clientId = "xxx"

        val request = GetSignInIntentRequest.builder()
            .setServerClientId(clientId)
            .build()

        val intent = Identity.getSignInClient(this).getSignInIntent(request)

        intent.addOnSuccessListener { request ->
            startIntentSenderForResult(
                request.intentSender,
                1,
                null,
                0,
                0,
                0,
                null)
        }
        intent.addOnFailureListener { e-> Log.e("Error", "An error occurred") }
    }

    private fun initNavDrawer() {
        navigationController = Navigation.findNavController(findViewById(R.id.fragment))
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navigationController.graph, drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val headerView: View = navigationView.inflateHeaderView(R.layout.drawer_header)
        // val headerImage: CircularImageView = headerView.findViewById(R.id.circularImageId)
        val headerName: TextView = headerView.findViewById(R.id.headerName)
        headerName.setText(R.string.app_name)
        navigationView.setupWithNavController(navigationController)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            val credential = Identity.getSignInClient(this)
                    .getSignInCredentialFromIntent(data)

            val name = credential.displayName
            val familyName = credential.familyName
            val token = credential.googleIdToken
            val id = credential.id
            Log.i("info", "onActivityResult: " + name + " - " + familyName + " - " + id)

            val senator = Senator(name, familyName, id)
            Model.instance.putBean(getString(R.string.user), senator)

            // Store user details into shared preferences
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                //putInt(getString(R.string.saved_high_score_key), newHighScore)
                putString(getString(R.string.user_token), token)
                apply()
            }
            initNavDrawer()
        }
    }


}