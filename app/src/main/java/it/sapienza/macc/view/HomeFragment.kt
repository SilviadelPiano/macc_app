package it.sapienza.macc.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import it.sapienza.macc.BuildConfig
import it.sapienza.macc.R
import it.sapienza.macc.utils.LoginService
import it.sapienza.macc.model.Model
import it.sapienza.macc.model.Senator
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File


class HomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            try {
                val credential = Identity.getSignInClient(requireActivity()).getSignInCredentialFromIntent(data);
                val name = credential.displayName
                val familyName = credential.familyName
                val token = credential.googleIdToken
                val id = credential.id
                val picUri = credential.profilePictureUri
                Log.i("info", "onActivityResult: " + name + " - " + familyName + " - " + id)
                // TODO
                val senator = Senator(name, familyName, id, "", picUri)
                Model.instance.putBean(getString(R.string.user), senator)

                // Store user details into shared preferences
                val sharedPref = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putString(getString(R.string.user_token), token.toString())
                    apply()
                }
                var lservice = LoginService(context, this)
                lservice.sendLoginRequest(id, token.toString())
                updateView()
            } catch (e: ApiException) {
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT)
                Log.e("AUTH", e.message.toString())
                e.printStackTrace()
            }
        }
    }

    override fun onClick(v: View?) {
        val clientId = BuildConfig.CLIENT_ID
        //val clientId = "x"
        val request = GetSignInIntentRequest.builder()
                .setServerClientId(clientId)
                .build()

        val intent = Identity.getSignInClient(requireActivity()).getSignInIntent(request)

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

    fun updateView() {
        val senator: Senator = Model.instance.getBean("USER") as Senator
        welcomeTextView.setText("Welcome, " + senator.name)
        activity?.headerRole?.setText(senator.role)
        activity?.headerName?.setText(senator.name)
        Picasso.get().load(senator.profilePictureUri).error(R.drawable.person).fit()
                .centerInside().into(activity?.circularImageId);
    }




}