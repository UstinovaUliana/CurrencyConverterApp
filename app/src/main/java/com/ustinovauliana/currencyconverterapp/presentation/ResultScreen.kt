package com.ustinovauliana.currencyconverterapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ustinovauliana.currencyconverterapp.R

@Composable
internal fun ResultScreen(
    currencyViewModel: CurrencyConverterViewModel
) {
    var result: String by remember { mutableStateOf("") }
    val state by currencyViewModel.state.collectAsState()
    val secondCurrencyText = currencyViewModel.secondCurrencyText
    val amount = currencyViewModel.amount

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            is State.Success -> {
                var conversionCoefficient: Float? = state.currencyMap?.get(secondCurrencyText)?.value
                if (conversionCoefficient == null) conversionCoefficient = 1f
                result = (amount.toFloat() * conversionCoefficient).toString()
                Column {
                    Text(
                        stringResource(R.string.the_result_is),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        "$result $secondCurrencyText",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp
                    )
                }
            } else -> {
                CircularProgressIndicator()
            }
        }
    }
}
