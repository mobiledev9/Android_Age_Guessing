package technologies.akkas.ageguess.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.MobileAds;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.adapters.AdapterViewPagerSwipe;
import technologies.akkas.ageguess.network.ApiClientKT;
import technologies.akkas.ageguess.network.ApiInterfaceKT;
import technologies.akkas.ageguess.network.model.SignInPOJO;
import technologies.akkas.ageguess.utils.Preferences;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    AdapterViewPagerSwipe adapterViewPagerSwipe;
    private static final int REQUEST_CODE_LOCATION = 100;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        AndroidThreeTen.init(this);
//        init();


        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, new TypeSelectorFragment());
        transaction.commit();

        MobileAds.initialize(this, initializationStatus -> {
        });

        getHashkey();
        checkLocationPermission();
    }

    private void init() {
        viewPager = findViewById(R.id.viewpager_activitywelcome_pager);

        setViewPagerAdapter();
    }

    private void setViewPagerAdapter() {

        adapterViewPagerSwipe = new AdapterViewPagerSwipe(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPagerSwipe);
        viewPager.setPageTransformer(false, new DefaultTransformer());

    }

    public void getHashkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.i("Base64", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            // REQUEST_CODE_LOCATION should be defined on your app level
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION && grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Location services are required in order to " +
                    "connect to a reader.");
        }
    }
}
