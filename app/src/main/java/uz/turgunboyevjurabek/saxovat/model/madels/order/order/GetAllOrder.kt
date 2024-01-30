package uz.turgunboyevjurabek.saxovat.model.madels.order.order


import com.google.gson.annotations.SerializedName

data class GetAllOrder(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: ArrayList<Result>
)