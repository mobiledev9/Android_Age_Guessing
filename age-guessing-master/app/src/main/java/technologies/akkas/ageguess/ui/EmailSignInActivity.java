package technologies.akkas.ageguess.ui;

import android.content.Context;
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

public class EmailSignInActivity extends AppCompatActivity implements View.OnClickListener {

    Context context = this;
    TextInputEditText inputEmail;
    TextInputEditText inputPassword;
    Button btnSignIn;
    TextView tvSignUp;
    ImageView imglogo;
    TextInputLayout layoutEmail;
    TextInputLayout layoutPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_email);
        init();
    }

    private void init() {
    inputEmail=findViewById(R.id.inputedit_email);
    inputPassword=findViewById(R.id.input_edit_password);
    btnSignIn=findViewById(R.id.btn_signin);
    tvSignUp=findViewById(R.id.tv_signup);
    imglogo=findViewById(R.id.imglogo);
    layoutEmail=findViewById(R.id.layout_email);
    layoutPassword=findViewById(R.id.layout_password);


    tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_signup:
            {
                Intent intent=new Intent(context,SignInWithActivity.class);
                intent.putExtra("title",getString(R.string.signupwith));
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
        Intent intent=new Intent(context,SignInWithActivity.class);
        intent.putExtra("title",getString(R.string.signin_with));
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
        layoutEmail.setAnimation(animation);
        layoutPassword.setAnimation(animation);
        btnSignIn.setAnimation(animation);
        tvSignUp.setAnimation(animation);

    }
}
