package com.ustinovauliana.currencyconverterapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ustinovauliana.currencyconverterapp.domain.GetCurrencyValueUseCase
import com.ustinovauliana.currencyconverterapp.presentation.models.CurrencyUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class CurrencyConverterViewModel @Inject constructor(
    val getCurrencyValueUseCase: Provider<GetCurrencyValueUseCase>
) : ViewModel() {
    /*
        var state = flowOf(mapOf("USD" to CurrencyUI(code = "USD", value = 1f)))
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)

        fun getCurrencyValue(currency: String, baseCurrency: String?) {
            state = getCurrencyValueUseCase.get().invoke(currency = currency, baseCurrency = baseCurrency)
                .map { it.toState() }
                .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
        }

     */
    private val _state = MutableStateFlow<State>(State.None)
    val state: StateFlow<State> get() = _state.asStateFlow()

    fun getCurrencyValue(currency: String, baseCurrency: String?){
        viewModelScope.launch {
            getCurrencyValueUseCase.get().invoke(currency = currency, baseCurrency = baseCurrency)
                .map { it.toState() }
                .collect{
                    _state.value = it
            }
        }
    }
}

private fun Map<String, CurrencyUI>.toState(): State {
    return when (this) {
        checkNotNull(this) -> State.Success(this)
        else -> State.Loading(this)
    }
}

internal sealed class State(val currencyMap: Map<String, CurrencyUI>?) {

    data object None : State(currencyMap = null)
    class Loading(currencyMap: Map<String, CurrencyUI>? = null) : State(currencyMap)
    class Error(currencyMap: Map<String, CurrencyUI>? = null) : State(currencyMap)
    class Success(currencyMap: Map<String, CurrencyUI>) : State(currencyMap)
}
