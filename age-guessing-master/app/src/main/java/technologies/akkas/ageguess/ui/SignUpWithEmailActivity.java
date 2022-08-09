package technologies.akkas.ageguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import technologies.akkas.ageguess.R;

public class SignUpWithEmailActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText inputUserName;
    TextInputEditText inputEmail;
    TextInputEditText inputPassword;
    TextInputEditText inputConfirmPassword;
    Button btnSignUp;
    TextView tvSignIn;
    ImageView imglogo;
    TextInputLayout layoutUserName;
    TextInputLayout layoutEmail;
    TextInputLayout layoutPassword;
    TextInputLayout layoutConfirmPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_withemail_activity);

        init();

    }

    private void init() {

        inputUserName=findViewById(R.id.inputUserName);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputCofirmPassword);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvSignIn=findViewById(R.id.tvSignIn);
        imglogo=findViewById(R.id.imglogo);
        layoutUserName=findViewById(R.id.inputlayout_username);
        layoutEmail=findViewById(R.id.inputlayout_email);
        layoutPassword=findViewById(R.id.inputlayout_password);
        layoutConfirmPassword=findViewById(R.id.inputlayout_confirmpassword);

        tvSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tvSignIn:
            {
                Intent intent=new Intent(SignUpWithEmailActivity.this,SignInWithActivity.class);
                intent.putExtra("title",getString(R.string.signin_with));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(SignUpWithEmailActivity.this,SignInWithActivity.class);
        intent.putExtra("title",getString(R.string.signupwith));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        AnimationOnLogo();
        SlideInputLayout();

    }
    private void AnimationOnLogo() {
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
        imglogo.startAnimation(animation);
    }
    private void SlideInputLayout() {
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.transalate_fromx);
        layoutUserName.setAnimation(animation);
        layoutEmail.setAnimation(animation);
        layoutPassword.setAnimation(animation);
        layoutConfirmPassword.setAnimation(animation);
        btnSignUp.setAnimation(animation);
        tvSignIn.setAnimation(animation);
    }
}
