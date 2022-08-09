package technologies.akkas.ageguess.network;

import retrofit2.Call
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import technologies.akkas.ageguess.network.model.BaseResponsePOJO
import technologies.akkas.ageguess.network.model.SignInPOJO
import technologies.akkas.ageguess.network.model.stripe_intent.StripeIntentResponse

interface ApiInterfaceKT {

    @FormUrlEncoded
    @POST("register")
    fun signIn(@FieldMap parameters: HashMap<String, String>): Call<SignInPOJO>

    @FormUrlEncoded
    @POST("checkPayment")
    fun checkPaymentStatus(@FieldMap parameters: HashMap<String, String>): Call<BaseResponsePOJO>

    @FormUrlEncoded
    @POST("transaction")
    fun transaction(@FieldMap parameters: HashMap<String, String>): Call<BaseResponsePOJO>

    @FormUrlEncoded
    @POST("payment_intents")
    fun getPaymentIntent(@FieldMap parameters: HashMap<String, String>): Call<StripeIntentResponse>
}