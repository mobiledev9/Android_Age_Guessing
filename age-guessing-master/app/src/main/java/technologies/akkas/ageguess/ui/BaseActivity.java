package technologies.akkas.ageguess.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.interfaces.OnAdsCallback;
import technologies.akkas.ageguess.utils.Preferences;

public class BaseActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private static String TAG = "BaseActivity";
    private OnAdsCallback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, initializationStatus -> {
        });

        adRequest = new AdRequest.Builder().build();
        inItAds();

    }

    public void inItAds() {
        com.google.android.gms.ads.interstitial.InterstitialAd.load(this, getString(R.string.admob_inter_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");
                initCallback();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });


    }

    private void initCallback() {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
                callback.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
                callback.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
            }
        });
    }

    public boolean onAdsLoaded() {
        if (mInterstitialAd != null && Preferences.getBool(this, Preferences.KEY_SHOW_ADS)) {
            return true;
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.");
            return false;
        }
    }

    public void showAds(OnAdsCallback interfaces) {
        callback = interfaces;
        if (mInterstitialAd != null && Preferences.getBool(this, Preferences.KEY_SHOW_ADS)) {
            mInterstitialAd.show(this);

        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.");
        }
    }
}
