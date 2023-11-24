package com.example.matchtransactions.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchtransactions.R

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    paidTo: String,
    transactionDate: String,
    total: Float,
    docType: String,
    isSelected: Boolean) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 16.dp),
                    checked = isSelected,
                    onCheckedChange = null
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                ) {
                    Column(
                        Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = paidTo,
                            fontSize = dimensionResource(id = R.dimen.text_size_regular).value.sp,
                            color = colorResource(id = R.color.text_regular)
                        )
                        Text(
                            text = transactionDate,
                            fontSize = dimensionResource(id = R.dimen.text_size_subtext).value.sp,
                            color = colorResource(id = R.color.text_subtext)
                        )
                    }
                    Column(
                        Modifier.align(Alignment.CenterEnd),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = total.toString(),
                            fontSize = dimensionResource(id = R.dimen.text_size_regular).value.sp,
                            color = colorResource(id = R.color.text_regular)
                        )
                        Text(
                            text = docType,
                            fontSize = dimensionResource(id = R.dimen.text_size_subtext).value.sp,
                            color = colorResource(id = R.color.text_subtext)
                        )
                    }
                }
            }
    }
}