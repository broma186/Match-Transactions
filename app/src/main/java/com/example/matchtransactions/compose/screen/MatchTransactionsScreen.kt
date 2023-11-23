package com.example.matchtransactions.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.matchtransactions.R
import com.example.matchtransactions.compose.components.TransactionItem
import com.example.matchtransactions.compose.viewmodel.FindMatchViewModel
import com.example.matchtransactions.models.Transaction

@Composable
fun MatchTransactionsScreen(
    findMatchViewModel: FindMatchViewModel = FindMatchViewModel(),
    exit: () -> Unit
) {
    MatchTransactionsScreen(
        findMatchViewModel.remainingTotal.value,
        findMatchViewModel.transactions.value.transactionList,
        findMatchViewModel::checkForSingleTransaction,
        findMatchViewModel::onTransactionSelected,
        exit)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchTransactionsScreen(
    remainingTotal: Float,
    transactions: List<Transaction>,
    checkForSingleTransaction: () -> Unit,
    onTransactionSelected: (index: Int, total: Float) -> Unit,
    exit: () -> Unit
) {
    LaunchedEffect("CheckForSingleTrans") {
        checkForSingleTransaction.invoke()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(4.dp),
                title = {
                    stringResource(R.string.title_find_match)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            exit.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.select_matches, remainingTotal)
                )
            }
            transactions.forEachIndexed { index, transaction ->
                TransactionItem(
                    index,
                    transaction.paidTo,
                    transaction.transactionDate,
                    transaction.total,
                    transaction.docType,
                    transaction.isSelected
                ) {
                    onTransactionSelected(it, transaction.total)
                }
            }
        }
    }
}