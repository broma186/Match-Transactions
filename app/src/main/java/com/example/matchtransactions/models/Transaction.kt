package com.example.matchtransactions.models

data class Transaction(
    val paidTo: String,
    val transactionDate: String,
    val total: Float,
    val docType : String,
    val isSelected: Boolean
)