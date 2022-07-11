package com.ecorner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class user (
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: String = "",
    val gender: String = "",
    val profileCompleted: Int = 0
):Parcelable