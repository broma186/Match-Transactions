package com.example.matchtransactions.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TransactionItem(
    index: Int,
    paidTo: String,
    transactionDate: String,
    total: Float,
    docType: String,
    isSelected: Boolean,
    onSelected: (index: Int) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(isSelected) {
                onSelected.invoke(index)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isSelected, onCheckedChange = null)
        Box {
            Column(
                Modifier.align(Alignment.CenterStart),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = paidTo
                )
                Text(
                    text = transactionDate
                )
            }
            Column(
                Modifier.align(Alignment.CenterEnd),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = total.toString()
                )
                Text(
                    text = docType
                )
            }
        }
    }
}