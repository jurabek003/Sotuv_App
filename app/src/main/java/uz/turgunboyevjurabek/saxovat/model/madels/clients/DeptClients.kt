package uz.turgunboyevjurabek.saxovat.model.madels.clients


import com.google.gson.annotations.SerializedName

data class DeptClients(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
)