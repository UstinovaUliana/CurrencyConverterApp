package com.ustinovauliana.features.main

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ConverterMainScreen() {
    ConverterMainScreen(currencyViewModel = viewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConverterMainScreen(currencyViewModel: CurrencyConverterViewModel) {

   // val state by currencyViewModel.state.collectAsState()
    var amount: String by rememberSaveable {
        mutableStateOf("")
    }
    var result: String by remember { mutableStateOf("") }
    var isFirstExpanded by remember { mutableStateOf(false) }
    var isSecondExpanded by remember { mutableStateOf(false) }
    val currencies = arrayOf("EUR", "GBP", "USD", "RUB")
    var firstCurrencyText by remember { mutableStateOf(currencies[0]) }
    var secondCurrencyText by remember { mutableStateOf(currencies[1]) }

    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.statusBars.getTop(density) / density.density

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight.dp),
    ) {
        Text("Enter amount:",)
        Row (
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextField(
                value = amount,
                onValueChange = { onAmountChanged: String ->
                    amount = onAmountChanged
                },
                Modifier.weight(2f)
                    .padding(5.dp)
            )

            ExposedDropdownMenuBox(
                expanded = isFirstExpanded,
                onExpandedChange = {
                    isFirstExpanded = !isFirstExpanded
                },
                Modifier.weight(1f)
                    .padding(5.dp)
            ) {
                TextField(
                    value = firstCurrencyText,
                    onValueChange = {
                    },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isFirstExpanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isFirstExpanded,
                    onDismissRequest = { isFirstExpanded = false }
                ) {
                    currencies.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                firstCurrencyText = item
                                isFirstExpanded = false

                                if (secondCurrencyText == firstCurrencyText) {
                                    secondCurrencyText = currencies.filter { it != firstCurrencyText }[1]
                                }
                            }
                        )
                    }
                }
            }
        }
        Text("Convert To:",)

        ExposedDropdownMenuBox(
            expanded = isSecondExpanded,
            onExpandedChange = {
                isSecondExpanded = !isSecondExpanded
            },
        ) {
            TextField(
                value = secondCurrencyText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSecondExpanded) },
                modifier = Modifier.menuAnchor()
                    .weight(1f)
            )

            ExposedDropdownMenu(
                expanded = isSecondExpanded,
                onDismissRequest = { isSecondExpanded = false },
                modifier = Modifier.weight(1f)
            ) {
                currencies
                    .filter {
                        it != firstCurrencyText
                    }
                    .forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                secondCurrencyText = item
                                isSecondExpanded = false
                            }
                        )
                    }
            }
        }

        Button(onClick = {
            /*
            currencyViewModel.getCurrencyValue(baseCurrency =  firstCurrencyText, currency = secondCurrencyText)

             */
            result = (amount.toFloat()*0.333).toString()
        },) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.exchange_svgrepo_com),
                    contentDescription = "Image",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(Modifier.size(10.dp))

                Text(
                    "Convert",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )

            }
        }

        Text(if (result !="") result else "summ",
            textAlign = TextAlign.Center,)
        Spacer(Modifier.size(100.dp))
    }
}
