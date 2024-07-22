package com.ustinovauliana.currencyconverterapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ustinovauliana.currencyconverterapp.domain.GetCurrencyValueUseCase
import com.ustinovauliana.currencyconverterapp.presentation.models.CurrencyUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class CurrencyConverterViewModel @Inject constructor(
    private val getCurrencyValueUseCase: Provider<GetCurrencyValueUseCase>
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.None)
    val state: StateFlow<State> get() = _state.asStateFlow()
    var secondCurrencyText: String = "GBP"
    var amount: String = "100"
    fun getCurrencyValue(currency: String, baseCurrency: String?) {
        viewModelScope.launch {
            getCurrencyValueUseCase.get()
                .invoke(currency = currency, baseCurrency = baseCurrency)
                .map { it.toState() }
                .catch { e ->
                    println("Exception ${e.message}")
                    emit(State.Error(null))
                }
                .collect {
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
