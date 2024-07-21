package com.ustinovauliana.currencyconverterapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ustinovauliana.currencyconverterapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConverterMainScreen(currencyViewModel: CurrencyConverterViewModel,
                                 navController: NavController
) {

    val state by currencyViewModel.state.collectAsState()

    var amount: String by rememberSaveable {
        mutableStateOf("100")
    }

    var isFirstExpanded by remember { mutableStateOf(false) }
    var isSecondExpanded by remember { mutableStateOf(false) }

    val currencies = arrayOf("EUR", "GBP", "USD", "RUB")
    var firstCurrencyText by rememberSaveable { mutableStateOf(currencies[0]) }
    var secondCurrencyText by rememberSaveable { mutableStateOf(currencies[1]) }

    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.statusBars.getTop(density) / density.density

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Convert any currency",
            fontSize = 30.sp,
            modifier = Modifier
                .weight(0.5f)
        )
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(16.dp)
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                TextField(
                    value = amount,
                    onValueChange = { onAmountChanged: String ->
                        amount = onAmountChanged
                    },
                    Modifier
                        .weight(1f)
                        .padding(5.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = isFirstExpanded,
                    onExpandedChange = {
                        isFirstExpanded = !isFirstExpanded
                    },
                    Modifier
                        .weight(1f)
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
                                        secondCurrencyText =
                                            currencies.filter { it != firstCurrencyText }[1]
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Text(
                "Convert To:",
            )

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
                    modifier = Modifier
                        .menuAnchor()
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
        }
        Button(
            onClick = {
                currencyViewModel.getCurrencyValue(
                    baseCurrency = if (firstCurrencyText == "USD") null else firstCurrencyText,
                    currency = secondCurrencyText
                )
                currencyViewModel.secondCurrencyText = secondCurrencyText
                currencyViewModel.amount = amount
                navController.navigate("result") {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.exchange_svgrepo_com),
                    contentDescription = "Image",
                    modifier = Modifier
                        .weight(2f)
                )
                Spacer(Modifier.size(10.dp))

                Text(
                    "Convert",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                )

            }
        }
    }
}

@Composable
internal fun ResultScreen(
    currencyViewModel: CurrencyConverterViewModel
) {
    var result: String by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
    val state by currencyViewModel.state.collectAsState()
    val secondCurrencyText = currencyViewModel.secondCurrencyText
    val amount = currencyViewModel.amount

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
    ) {

        visibility = true
        AnimatedVisibility(
            visible = visibility,
            enter = scaleIn(),
            exit = scaleOut(),
        ) {
            when (state) {
                is State.Success -> {
                    var k: Float? = if (state.currencyMap != null) {
                        val map = state.currencyMap
                        map?.get(secondCurrencyText)?.value
                    } else 0.25f
                    if (k == null) k = 1f
                    result = (amount.toFloat() * k).toString()
                    Text(
                        "$result $secondCurrencyText",
                        textAlign = TextAlign.Center,
                    )
                }

                else -> {
                    CircularProgressIndicator()
                }
            }
        }

    }
}
