package technologies.akkas.ageguess.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_show_result.img_share
import kotlinx.android.synthetic.main.fragment_type_selector.*
import retrofit2.Call
import retrofit2.Response
import technologies.akkas.ageguess.GlobalClass
import technologies.akkas.ageguess.R
import technologies.akkas.ageguess.birthday_at.BirthdayAtActivity
import technologies.akkas.ageguess.network.ApiClientKT
import technologies.akkas.ageguess.network.ApiInterfaceKT
import technologies.akkas.ageguess.network.model.BaseResponsePOJO
import technologies.akkas.ageguess.network.model.stripe_intent.StripeIntentResponse
import technologies.akkas.ageguess.utils.Preferences


class TypeSelectorFragment : Fragment(), View.OnClickListener {

    lateinit var guessAgeBt: ConstraintLayout
    lateinit var babyBirthdayBt: ConstraintLayout
    lateinit var mAdView: AdView
    lateinit var atSpecific: ConstraintLayout
    lateinit var addFree: ConstraintLayout
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_type_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        val parameters = java.util.HashMap<String, String>()
        parameters["user_id"] = Preferences.get(context, Preferences.KEY_USER_ID)
        callCheckPayment(parameters);
    }

    private fun initViews(view: View) {
        guessAgeBt = view.findViewById(R.id.guess_age_bt)
        babyBirthdayBt = view.findViewById(R.id.baby_birthday_bt)
        atSpecific = view.findViewById(R.id.at_specific)
        addFree = view.findViewById(R.id.addFree)
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        atSpecific.setOnClickListener(this)
        guessAgeBt.setOnClickListener(this)
        babyBirthdayBt.setOnClickListener(this)
        addFree.setOnClickListener(this)
        img_remove_ads.setOnClickListener(this)
        img_share.setOnClickListener {
            GlobalClass.shareApp(activity)
        }
        imgLogout.setOnClickListener {

            AlertDialog.Builder(context).setTitle("Logout")
                    .setMessage("Are you sure you want to logout from the application ?")
                    .setPositiveButton("Logout") { dialog, which ->
                        if(AccessToken.getCurrentAccessToken()!=null){
                            AccessToken.setCurrentAccessToken(null);
                        }
                        LoginManager.getInstance().logOut()

                        /*Logout Google SignIn*/
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                        val googleSignInClient = GoogleSignIn.getClient(context!!, gso)
                        googleSignInClient.signOut()

                        /*Clear Preference*/
                        Preferences.clearPreference(context)

                        /*Start Sign In Activity*/
                        startActivity(Intent(mContext, SignInWithActivity::class.java))

                        /*Finish All the activities*/
                        (mContext as Activity).finishAffinity()
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }.show()

        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.baby_birthday_bt -> {
                startActivity(Intent(activity, ExpectedBirthdayActivity::class.java))
                //activity?.finish()
            }
            R.id.guess_age_bt -> {
                startActivity(Intent(activity, AskUserForAgeActivity::class.java))
                //activity?.finish()
            }
            R.id.at_specific -> {
                startActivity(Intent(activity, BirthdayAtActivity::class.java))
                //activity?.finish()
            }
            R.id.img_remove_ads -> {
                paymentDialog()
            }
            R.id.addFree -> {
                paymentDialog()
            }
        }
    }

    private fun paymentDialog() {
        AlertDialog.Builder(context).setTitle("Remove Ads").setMessage("For removing the ads permanently we will charge you 1$.")
                .setPositiveButton("Continue") { dialog, which -> getPaymentIntent() }
                .setNegativeButton("Cancel", { dialog, w -> dialog.dismiss() })
                .show()
    }

    private fun getPaymentIntent() {
        val api = ApiClientKT().getStripeClient()!!.create(ApiInterfaceKT::class.java)
        showHideProgress(true)

        val hashMap = HashMap<String, String>()
        hashMap["amount"] = "100"
        hashMap["currency"] = "usd"

        api.getPaymentIntent(hashMap).enqueue(object : retrofit2.Callback<StripeIntentResponse> {
            override fun onResponse(call: Call<StripeIntentResponse>, response: Response<StripeIntentResponse>) {
                showHideProgress(false)
                if (response.isSuccessful) {
                    startActivity(Intent(activity, StripeCheckoutActivity::class.java).putExtra("client_secret", response.body()?.client_secret))
                }
            }

            override fun onFailure(call: Call<StripeIntentResponse>, t: Throwable) {
                t.printStackTrace()
                showHideProgress(false)
            }

        })

    }

    private fun showHideProgress(isShow: Boolean) {
        if(progressBar != null){
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    private fun callCheckPayment(parameters: java.util.HashMap<String, String>) {
        val api = ApiClientKT().getClient()!!.create(ApiInterfaceKT::class.java)
        showHideProgress(true)
        api.checkPaymentStatus(parameters).enqueue(object : retrofit2.Callback<BaseResponsePOJO> {
            override fun onResponse(call: Call<BaseResponsePOJO>, response: Response<BaseResponsePOJO>) {
                showHideProgress(false)
                if (response.isSuccessful && response.body()!!.status == 1) {
                    Preferences.saveBool(context, Preferences.KEY_SHOW_ADS, false)
                    img_remove_ads.visibility = View.GONE
//                    addFree.visibility = View.GONE
                    mAdView.visibility = View.GONE
                } else {
                    Preferences.saveBool(context, Preferences.KEY_SHOW_ADS, true)
                    img_remove_ads.visibility = View.VISIBLE
//                    addFree.visibility = View.VISIBLE
                    mAdView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<BaseResponsePOJO>, t: Throwable) {
                t.printStackTrace()
                showHideProgress(false)
            }
        })
    }
}
