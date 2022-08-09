package technologies.akkas.ageguess.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.network.ApiClientKT;
import technologies.akkas.ageguess.network.ApiInterfaceKT;
import technologies.akkas.ageguess.network.model.SignInPOJO;
import technologies.akkas.ageguess.utils.Preferences;

public class SignInWithActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "SignInWithActivity";
    Context context = this;
    Button btnFacebook;
    Button btnGooglePlus;
    Button btnEmailSignIn;
    TextView tvTitle;
    TextView tvBottom;
    String title;
    ImageView imglogo;
    LoginButton fbLoginBtn;
    CallbackManager callbackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1001;
    ProgressBar progressBar;
    AccessToken accessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singinwith_acitvity);
        init();

        if (!Preferences.get(context, Preferences.KEY_USER_ID).equals("") && !Preferences.get(context, Preferences.KEY_USER_ID).equals("0")) {
            startActivity(new Intent(context, WelcomeActivity.class));
            finishAffinity();
            return;
        }

        initFbManager();
        initGoogleLogin();
    }

    private void initFbManager() {

        btnFacebook.setOnClickListener(v -> fbLoginBtn.performClick());

        fbLoginBtn.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();

        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                getFbDetails(accessToken);
                //startActivity(new Intent(context, WelcomeActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("SHUBHAM---"+error.toString());
            }
        });
    }

    private void getFbDetails(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        //   Toast.makeText(Login.this, object.toString(), Toast.LENGTH_LONG).show();
                        Log.v("FB Details", object.toString());
                        if (object != null) {
                            HashMap<String, String> parameters = new HashMap<>();
                            parameters.put("type", "facebook");
                            parameters.put("social_media_id", accessToken.getUserId());
                            parameters.put("first_name",object.optString("name"));
                            parameters.put("last_name", "");
                            parameters.put("email", object.optString("email"));
                            callSignInApi(parameters);

                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name, last_name, email,link");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void initGoogleLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void startGoogleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("SHUBH", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("SHUBH", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("SHUBH", "printHashKey()", e);
        }
    }

    private void init() {
        printHashKey(this);
        title = getIntent().getStringExtra("title");
        btnFacebook = findViewById(R.id.btn_facebook_signinwith);
        btnGooglePlus = findViewById(R.id.btngoogleplus_signinwith);
        btnEmailSignIn = findViewById(R.id.btn_email_signin_with);
        tvTitle = findViewById(R.id.tv_title_signinwith);
        tvBottom = findViewById(R.id.tv_bottom_signinwith);
        imglogo = findViewById(R.id.imglogo);
        fbLoginBtn = findViewById(R.id.fbLogin);
        progressBar = findViewById(R.id.progressBar);

        btnGooglePlus.setOnClickListener(v -> startGoogleLogin());

        if (title == null) {
            title = getString(R.string.signin_with);
        }
        tvTitle.setText("" + title);
        if (title.equals(getString(R.string.signupwith))) {
            tvBottom.setText("" + getString(R.string.signin));
            btnEmailSignIn.setText("" + getString(R.string.email_sign_up));
        } else {
            tvBottom.setText("" + getString(R.string.signup));
            btnEmailSignIn.setText("" + getString(R.string.email_sign_in));
        }

        tvBottom.setOnClickListener(this);
        btnEmailSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bottom_signinwith: {
                if (tvBottom.getText().toString().equals(getString(R.string.signup))) {
                    tvTitle.setText("" + getString(R.string.signupwith));
                    tvBottom.setText("" + getString(R.string.orsignin));
                    btnEmailSignIn.setText("" + getString(R.string.email_sign_up));

                } else {
                    tvTitle.setText("" + getString(R.string.signin_with));
                    tvBottom.setText("" + getString(R.string.signup));
                    btnEmailSignIn.setText("" + getString(R.string.email_sign_in));
                }

                break;
            }
            case R.id.btn_email_signin_with: {
                if (tvTitle.getText().equals(getString(R.string.signupwith))) {
                    Intent intent = new Intent(context, SignUpWithEmailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    this.finish();
                } else {
                    Intent intent = new Intent(context, EmailSignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    this.finish();
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationOnLogo();
        SlideInputLayout();

    }

    private void AnimationOnLogo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        imglogo.startAnimation(animation);
    }

    private void SlideInputLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transalate_fromx);
        tvTitle.setAnimation(animation);
        btnFacebook.setAnimation(animation);
        btnGooglePlus.setAnimation(animation);
        btnEmailSignIn.setAnimation(animation);
        tvBottom.setAnimation(animation);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {

                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("type", "google");
                parameters.put("social_media_id", account.getId());
                parameters.put("first_name", account.getDisplayName());
                parameters.put("last_name", "");
                parameters.put("email", account.getEmail());
                callSignInApi(parameters);
            }

        } catch (ApiException e) {
            Toast.makeText(context, "Sign In Failed due to some internal error.", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void callSignInApi(HashMap<String, String> parameters) {
        ApiInterfaceKT api = new ApiClientKT().getClient().create(ApiInterfaceKT.class);
        showHideProgress(true);

        api.signIn(parameters).enqueue(new Callback<SignInPOJO>() {
            @Override
            public void onResponse(Call<SignInPOJO> call, Response<SignInPOJO> response) {
                showHideProgress(false);
                if (response.isSuccessful() && response.body().getStatus() == 1) {
                    Preferences.save(context, Preferences.KEY_USER_ID, response.body().getData().toString());
                    startActivity(new Intent(context, WelcomeActivity.class));
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<SignInPOJO> call, Throwable t) {
                showHideProgress(false);
            }
        });
    }

    private void showHideProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        btnFacebook.setEnabled(!isShow);
        btnGooglePlus.setEnabled(!isShow);
        fbLoginBtn.setEnabled(!isShow);
    }
}
