package com.example.matchtransactions.compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.matchtransactions.models.Transaction
import com.example.matchtransactions.models.Transactions

class MatchTransactionsViewModel(
    data: Transactions
) : ViewModel() {

    companion object {
        const val TARGET_MATCH_VALUE = 10000f

        val transactions = Transactions(
            listOf(
                Transaction("City Limousines", "30 Aug", 249.00f, "Sales Invoice", false),
                Transaction("Ridgeway University", "12 Sep", 618.50f, "Sales Invoice", false),
                Transaction("Cube Land", "22 Sep", 495.00f, "Sales Invoice", false),
                Transaction("Bayside Club", "23 Sep", 234.00f, "Sales Invoice", false),
                Transaction("SMART Agency", "12 Sep", 250f, "Sales Invoice", false),
                Transaction("PowerDirect", "11 Sep", 108.60f, "Sales Invoice", false),
                Transaction("PC Complete", "17 Sep", 216.99f, "Sales Invoice", false),
                Transaction("MCO Cleaning services", "17 Sep", 170.50f, "Sales Invoice", false),
                Transaction("Gateway Motors", "18 Sep", 411.35f, "Sales Invoice", false)
            )
        )
    }

    private val _remainingTotal: MutableState<Float> = mutableFloatStateOf(TARGET_MATCH_VALUE)
    val remainingTotal: State<Float> = _remainingTotal

    private val _transactions: MutableState<Transactions> = mutableStateOf(data)
    val transactions: State<Transactions> = _transactions

    fun checkForSingleTransaction() {
        _transactions.value.transactionList.forEachIndexed { index, transaction ->
            if (transaction.total == _remainingTotal.value) {
                toggleTransactionItemSelectionWithIndex(index)
                _remainingTotal.value = 0f
            }
        }
    }

    fun onTransactionSelected(index: Int) {
        toggleTransactionItemSelectionWithIndex(index)
        updateRemainingTotal(selectedTransaction = _transactions.value.transactionList[index])
    }

    fun updateRemainingTotal(selectedTransaction: Transaction) {
        if (selectedTransaction.isSelected) {
            _remainingTotal.value = _remainingTotal.value - selectedTransaction.total
        } else {
            _remainingTotal.value = _remainingTotal.value + selectedTransaction.total
        }
    }

    private fun toggleTransactionItemSelectionWithIndex(index: Int) {
        _transactions.value = _transactions.value.copy(
            transactionList = _transactions.value.transactionList.toMutableList().apply {
                this[index] = this[index].copy(isSelected = !this[index].isSelected)
            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val data: Transactions
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchTransactionsViewModel(data) as T
        }
    }
}