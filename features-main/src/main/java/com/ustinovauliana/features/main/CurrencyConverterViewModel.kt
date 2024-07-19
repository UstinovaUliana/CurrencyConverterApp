package com.ustinovauliana.features.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ustinovauliana.currency.data.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class CurrencyConverterViewModel @Inject constructor(
    val getCurrencyValueUseCase: Provider<GetCurrencyValueUseCase>
) : ViewModel() {

    var state = getCurrencyValueUseCase.get().invoke(currency = "USD", baseCurrency = "RUB")
        .map { it.toState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, State.None)

    fun getCurrencyValue(currency: String, baseCurrency: String?) {
        state = getCurrencyValueUseCase.get().invoke(currency = currency, baseCurrency = baseCurrency)
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
        Log.d("curdebug", "ViewModel: requestResult" + state.value.currency)
    }
}



private fun RequestResult<CurrencyUI>.toState(): State {
    return when (this) {
        is RequestResult.Success -> State.Success(data)
        is RequestResult.Error -> State.Error(data)
        is RequestResult.InProgress -> State.Loading(data)
    }
}

internal sealed class State(val currency: CurrencyUI?) {

    data object None : State(currency = null)
    class Loading(currency: CurrencyUI? = null) : State(currency)
    class Error(currency: CurrencyUI? = null) : State(currency)
    class Success(currency: CurrencyUI) : State(currency)
}
