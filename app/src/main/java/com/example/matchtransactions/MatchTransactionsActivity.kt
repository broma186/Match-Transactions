package com.example.matchtransactions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import com.example.matchtransactions.compose.screen.MatchTransactionsScreen

class MatchTransactionsActivity : AppCompatActivity() {

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View {
        return ComposeView(context).apply {
            setContent {
                MatchTransactionsScreen { finish() }
            }
        }
    }
}