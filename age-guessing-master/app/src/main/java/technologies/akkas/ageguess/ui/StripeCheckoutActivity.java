package technologies.akkas.ageguess.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technologies.akkas.ageguess.R;
import technologies.akkas.ageguess.network.ApiClientKT;
import technologies.akkas.ageguess.network.ApiInterfaceKT;
import technologies.akkas.ageguess.network.model.BaseResponsePOJO;
import technologies.akkas.ageguess.network.model.SignInPOJO;
import technologies.akkas.ageguess.utils.Preferences;

public class StripeCheckoutActivity extends AppCompatActivity {

    private String paymentIntentClientSecret = "";
    private Stripe stripe;
    CardInputWidget cardInputWidget;
    Context mContext = this;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_checkout);

        if (getIntent() != null) {
            paymentIntentClientSecret = getIntent().getStringExtra("client_secret");
        }

        cardInputWidget = findViewById(R.id.cardInputWidget);
        progressBar = findViewById(R.id.progressBar);

        startCheckout();
    }

    private void startCheckout() {
        // ...

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                final Context context = getApplicationContext();
                stripe = new Stripe(
                        context,
                        PaymentConfiguration.getInstance(context).getPublishableKey()
                );
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private  final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<StripeCheckoutActivity> activityRef;

        PaymentResultCallback(@NonNull StripeCheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripeCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String mResponse = gson.toJson(paymentIntent);



                if(paymentIntent.getStatus().getCode().equals("succeeded")){

                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("user_id",  Preferences.get(mContext, Preferences.KEY_USER_ID));
                    parameters.put("transaction_id", paymentIntent.getPaymentMethodId());
                    paymentSuccess(parameters);
                    Toast.makeText(mContext, "Payment Completed!", Toast.LENGTH_SHORT).show();

                }



            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed

                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripeCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
        }
    }


    private void showHideProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    private void paymentSuccess(HashMap<String, String> parameters) {
        ApiInterfaceKT api = new ApiClientKT().getClient().create(ApiInterfaceKT.class);
        showHideProgress(true);

        api.transaction(parameters).enqueue(new Callback<BaseResponsePOJO>() {
            @Override
            public void onResponse(Call<BaseResponsePOJO> call, Response<BaseResponsePOJO> response) {
                showHideProgress(false);
                if (response.isSuccessful() && response.body().getStatus() == 1) {
                    successAlert("Payment Success","Thank you for your payment!",true);
                }
            }

            @Override
            public void onFailure(Call<BaseResponsePOJO> call, Throwable t) {
                showHideProgress(false);
            }
        });
    }


    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Try Again!",
                    (DialogInterface dialog, int index) -> {
                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        startCheckout();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }


    private void successAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Ok",
                    (DialogInterface dialog, int index) -> {
                        startActivity(new Intent(mContext, WelcomeActivity.class));
                        finishAffinity();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }
}