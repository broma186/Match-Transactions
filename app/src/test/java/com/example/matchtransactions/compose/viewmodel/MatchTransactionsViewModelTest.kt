package com.example.matchtransactions.compose.viewmodel

import com.example.matchtransactions.models.Transaction
import com.example.matchtransactions.models.Transactions
import io.mockk.clearAllMocks
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MatchTransactionsViewModelTest {

    private var viewModel: MatchTransactionsViewModel? = null

    @Before
    fun setUp() {
        viewModel = MatchTransactionsViewModel(data = MatchTransactionsViewModel.transactions)
    }

    @Test
    fun `the initial value of the remaining total is set to 10000`() {
        Assert.assertEquals(viewModel?.remainingTotal?.value, MatchTransactionsViewModel.TARGET_MATCH_VALUE)
    }

    @Test
    fun `the the transaction data is set correctly`() {
        Assert.assertEquals(viewModel?.transactions?.value, MatchTransactionsViewModel.transactions)
    }

    @Test
    fun `given there is a transaction set to the remaining total initially, that transaction is selected and the remaining total is set to 0, when it is selected again, it deselects and the remaining total initialises again`() {
        viewModel = MatchTransactionsViewModel(data = Transactions(listOf(Transaction("City Limousines", "30 Aug", 10000f, "Sales Invoice", false))))
        viewModel?.checkForSingleTransaction()
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![0].isSelected, true)
        Assert.assertEquals(viewModel?.remainingTotal?.value, 0f)

        // Select again
        viewModel?.onTransactionSelected(0)
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![0].isSelected, false)
        Assert.assertEquals(viewModel?.remainingTotal?.value, 10000f)
    }

    @Test
    fun `given a deselected transaction is selected, the transaction is selected and the remaining total is updated accordingly`() {
        viewModel?.onTransactionSelected(4) // Select 'SMART Agency'
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![4].isSelected, true)
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![4].paidTo, "SMART Agency")
        Assert.assertEquals(viewModel?.remainingTotal?.value, 9750f)

        viewModel?.onTransactionSelected(1) // Select 'Ridgeway University'
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![1].isSelected, true)
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![1].paidTo, "Ridgeway University")
        Assert.assertEquals(viewModel?.remainingTotal?.value, 9131.5f)

        viewModel?.onTransactionSelected(4) // Deselect 'SMART Agency'
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![4].isSelected, false)
        Assert.assertEquals(viewModel?.remainingTotal?.value, 9381.5f)

        viewModel?.onTransactionSelected(1) // Deselect 'Ridgeway University'
        Assert.assertEquals(viewModel?.transactions?.value?.transactionList!![1].isSelected, false)
        Assert.assertEquals(viewModel?.remainingTotal?.value, 10000f)
    }

    @Test
    fun `given a transaction is selected or deselected, the remaining total is correctly incremented or decremented`() {
        // Selected
        val selectedTransaction = Transaction("PC Complete", "17 Sep", 216.99f, "Sales Invoice", true)
        viewModel?.updateRemainingTotal(selectedTransaction)
        Assert.assertEquals(viewModel?.remainingTotal?.value, 9783.01f)

        // Deselected
        val deselectedTransaction = Transaction("PC Complete", "17 Sep", 216.99f, "Sales Invoice", false)
        viewModel?.updateRemainingTotal(deselectedTransaction)
        Assert.assertEquals(viewModel?.remainingTotal?.value, 10000f)
    }

    @After
    fun tearDown() {
        clearAllMocks(answers = true, recordedCalls = true, childMocks = true, regularMocks = true, objectMocks = true, staticMocks = true, constructorMocks = true)
        viewModel = null
    }
}