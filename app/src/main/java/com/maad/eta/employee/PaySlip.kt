package com.maad.eta.employee

class PaySlip(
    val userId: String = "",
    val slipId: String = "",
    val status: String = "pending",
    val bonus: Int = 0,
    val socialInsurance: Int = 0,
    val bus: Boolean = false,
    val box: Int = 0,
    val healthInsurance: Int = 0,
    val absence: Int = 0,
    val date: String = "",
)