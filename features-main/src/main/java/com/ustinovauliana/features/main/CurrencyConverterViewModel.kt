package com.ustinovauliana.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ustinovauliana.currency.data.RequestResult
import com.ustinovauliana.currency.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class CurrencyConverterViewModel @Inject constructor(
   // val getCurrencyValueUseCase: Provider<GetCurrencyValueUseCase>
) : ViewModel() {
/*
    var state =

    fun getCurrencyValue(currency: String) {
        state = getCurrencyValueUseCase.get().invoke(currency = currency)
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
    }

 */
}



private fun RequestResult<List<CurrencyUI>>.toState(): State {
    return when (this) {
        is RequestResult.Success -> State.Success(data)
        is RequestResult.Error -> State.Error(data)
        is RequestResult.InProgress -> State.Loading(data)
    }
}

internal sealed class State(val articles: List<CurrencyUI>?) {

    data object None : State(articles = null)
    class Loading(articles: List<CurrencyUI>? = null) : State(articles)
    class Error(articles: List<CurrencyUI>? = null) : State(articles)
    class Success(articles: List<CurrencyUI>) : State(articles)
}
