package technologies.akkas.ageguess.network;

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import technologies.akkas.ageguess.BuildConfig
import java.util.concurrent.TimeUnit

public class ApiClientKT {

    private var retrofit: Retrofit? = null
    private var retrofitStripe: Retrofit? = null

    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logging)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .build()

        if (retrofit == null) {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
        }
        return retrofit
    }

    fun getStripeClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logging)
                .addInterceptor(BasicAuthInterceptor("sk_test_51IFKirFot7cN26EcJgocaZUTyfBru9AhZv2Ta0q7CSYv5su82wL88AEfJo232fRdfedlyzAVQo9CM8WRD6Az2aFP00d12D5Vns", "")).connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .build()

        if (retrofitStripe == null) {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            retrofitStripe = Retrofit.Builder()
                    .baseUrl(BuildConfig.STRIPE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
        }
        return retrofitStripe
    }

}