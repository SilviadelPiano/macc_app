package it.sapienza.macc.control

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import it.sapienza.macc.BuildConfig
import java.security.AccessController.getContext
import kotlinx.android.synthetic.main.fragment_home.*


class HomeControl : View.OnClickListener{

    private var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }


    override fun onClick(v: View) {
        Log.i("info", "button clicked!")
        val clientId = BuildConfig.CLIENT_ID
        //val clientId = "xxx"
        val request = GetSignInIntentRequest.builder()
            .setServerClientId(clientId)
            .build()

        val intent = Identity.getSignInClient(v?.context).getSignInIntent(request)

        intent.addOnSuccessListener { request ->
            startIntentSenderForResult(
                activity,
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

}