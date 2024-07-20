package com.ustinovauliana.features.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ustinovauliana.currency.data.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class CurrencyConverterViewModel @Inject constructor(
    val getCurrencyValueUseCase: Provider<GetCurrencyValueUseCase>
) : ViewModel() {

    var state = flowOf(mapOf("USD" to CurrencyUI(code = "USD", value = 1f)))
        .map { it.toState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, State.None)

    fun getCurrencyValue(currency: String, baseCurrency: String?) {
        state = getCurrencyValueUseCase.get().invoke(currency = currency, baseCurrency = baseCurrency)
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
    }
}

private fun Map<String,CurrencyUI>.toState(): State {
    return when (this) {
        checkNotNull(this) -> State.Success(this)
        else -> State.Loading(this)
    }
}

internal sealed class State(val currencyMap: Map<String,CurrencyUI>?) {

    data object None : State(currencyMap = null)
    class Loading(currencyMap: Map<String,CurrencyUI>? = null) : State(currencyMap)
    class Error(currencyMap: Map<String,CurrencyUI>? = null) : State(currencyMap)
    class Success(currencyMap: Map<String,CurrencyUI>) : State(currencyMap)
}
