import com.google.gson.annotations.SerializedName



data class Card (

		@SerializedName("installments") val installments : String,
		@SerializedName("network") val network : String,
		@SerializedName("request_three_d_secure") val request_three_d_secure : String
)