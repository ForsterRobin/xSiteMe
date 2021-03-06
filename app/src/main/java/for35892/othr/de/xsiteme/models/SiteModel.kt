package for35892.othr.de.xsiteme.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(
    var fbId: String ="",
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var visited: Boolean = false,
    var dateVisited: String = "",
    var favourite: Boolean = false,
    var rating: Float = 0f,
    var image: String = "",
    var additionalNotes: String = "",
    var lat: Double = 49.021273,
    var lng: Double = 12.098629,
    var zoom: Float = 15f
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

