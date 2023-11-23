package com.example.matchtransactions.compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.matchtransactions.models.Transaction
import com.example.matchtransactions.models.Transactions

class MatchTransactionsViewModel : ViewModel() {

    companion object {
        val TRANSACTIONS = arrayListOf(
            Transaction("City Limousines", "30 Aug", 249.00f, "Sales Invoice", false),
            Transaction("Ridgeway University", "12 Sep", 618.50f, "Sales Invoice", false),
            Transaction("Cube Land", "22 Sep", 495.00f, "Sales Invoice", false),
            Transaction("Bayside Club", "23 Sep", 234.00f, "Sales Invoice",  false),
            Transaction("SMART Agency", "12 Sep", 250f, "Sales Invoice", false),
            Transaction("PowerDirect", "11 Sep", 108.60f, "Sales Invoice", false),
            Transaction("PC Complete", "17 Sep", 216.99f, "Sales Invoice", false),
            Transaction("MCO Cleaning services", "17 Sep", 170.50f, "Sales Invoice", false),
            Transaction("Gateway Motors", "18 Sep", 411.35f, "Sales Invoice", false)
        )

        const val TARGET_MATCH_VALUE = 10000f
    }

    private val _remainingTotal: MutableState<Float> = mutableFloatStateOf(TARGET_MATCH_VALUE)
    val remainingTotal: State<Float> = _remainingTotal

    private val _transactions: MutableState<Transactions> = mutableStateOf(Transactions(TRANSACTIONS))
    val transactions: State<Transactions> = _transactions

    fun checkForSingleTransaction() { // Pass through true as we are definitely selecting the transaction regardless of whether it already is selected.
        _transactions.value.transactionList.forEachIndexed { index, transaction ->
            if (transaction.total == _remainingTotal.value) {
                toggleTransactionItemSelectionWithIndex(index, true)
            }
        }
    }

    fun onTransactionSelected(index: Int, total: Float) {
        toggleTransactionItemSelectionWithIndex(index)
        _remainingTotal.value = _remainingTotal.value - total
    }

    private fun toggleTransactionItemSelectionWithIndex(index: Int, isSelected: Boolean? = null) {
        _transactions.value = _transactions.value.copy(
            transactionList = _transactions.value.transactionList.toMutableList().apply {
                this[index] = this[index].copy(isSelected = isSelected ?: !this[index].isSelected)
            }
        )
    }
}