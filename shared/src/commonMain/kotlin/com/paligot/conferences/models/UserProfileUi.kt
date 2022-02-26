package com.paligot.conferences.models

import com.paligot.conferences.Image

data class UserProfileUi(
    val email: String,
    val firstName: String,
    val lastName: String,
    val company: String,
    val qrCode: Image
)
