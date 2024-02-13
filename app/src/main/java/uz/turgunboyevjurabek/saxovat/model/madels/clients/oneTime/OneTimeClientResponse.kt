package uz.turgunboyevjurabek.saxovat.model.madels.clients.oneTime


import com.google.gson.annotations.SerializedName

data class OneTimeClientResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("dept")
    val dept: Double,
    @SerializedName("email")
    val email: Any,
    @SerializedName("find_us")
    val findUs: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("info")
    val info: Any,
    @SerializedName("one_time")
    val oneTime: Boolean,
    @SerializedName("phones")
    val phones: List<Any>,
    @SerializedName("role")
    val role: String,
    @SerializedName("start_date")
    val startDate: Any,
    @SerializedName("updated_at")
    val updatedAt: String
)