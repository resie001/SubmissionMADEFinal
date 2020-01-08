package org.d3ifcool.submission.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var title : String? = null,
    var date : String? = null,
    var desc : String? = null,
    var image : String? = null
) : Parcelable