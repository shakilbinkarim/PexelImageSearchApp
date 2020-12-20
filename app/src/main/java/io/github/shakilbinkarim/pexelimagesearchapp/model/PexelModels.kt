package io.github.shakilbinkarim.pexelimagesearchapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PexelResponse(
    @SerializedName("photos")
    val results: List<PexelPhoto>
)

@Parcelize
data class PexelPhoto(
    val id: Int,
    val avg_color: String?,
    val height: Int?,
    val liked: Boolean?,
    val photographer: String?,
    val photographer_id: Int?,
    val photographer_url: String?,
    val src: Src,
    val url: String?,
    val width: Int?
) : Parcelable {
    @Parcelize
    data class Src(
        val landscape: String,
        val large: String,
        val large2x: String,
        val medium: String,
        val original: String,
        val portrait: String,
        val small: String,
        val tiny: String
    ) : Parcelable

}