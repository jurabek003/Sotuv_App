package uz.turgunboyevjurabek.saxovat.model.madels.categories.karzinka


import com.google.gson.annotations.SerializedName

data class GetAllCardOrder(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: ArrayList<Result>

)