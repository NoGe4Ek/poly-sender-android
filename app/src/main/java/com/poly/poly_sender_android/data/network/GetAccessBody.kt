package com.poly.poly_sender_android.data.network

data class GetAccessBody (
   val firstName: String,
   val lastName: String,
   val patronymic: String,
   val email: String,
   val department: String,
   val highSchool: String,
)