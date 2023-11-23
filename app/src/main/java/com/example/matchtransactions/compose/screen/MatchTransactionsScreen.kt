package com.example.matchtransactions.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchtransactions.R
import com.example.matchtransactions.compose.components.TransactionItem
import com.example.matchtransactions.compose.viewmodel.MatchTransactionsViewModel
import com.example.matchtransactions.models.Transaction

@Composable
fun MatchTransactionsScreen(
    matchTransactionsViewModel: MatchTransactionsViewModel = MatchTransactionsViewModel(),
    exit: () -> Unit
) {
    MatchTransactionsScreen(
        matchTransactionsViewModel.remainingTotal.value,
        matchTransactionsViewModel.transactions.value.transactionList,
        matchTransactionsViewModel::checkForSingleTransaction,
        matchTransactionsViewModel::onTransactionSelected,
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
    LaunchedEffect("CheckForSingleTransaction") {
        checkForSingleTransaction.invoke()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.colorPrimary)
                ),
                title = {
                    Text(stringResource(R.string.title_find_match),
                    color = Color.White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            exit.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(color = colorResource(id = R.color.colorPrimary)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.select_matches, remainingTotal),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.text_title_subtext),
                    fontSize = dimensionResource(id = R.dimen.text_size_subtext).value.sp,
                    maxLines = 1
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
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
}