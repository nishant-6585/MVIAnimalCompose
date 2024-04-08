package com.example.mvianimalscompose.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvianimalscompose.api.AnimalRepo
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: AnimalRepo): ViewModel() {

    val userIntent = kotlinx.coroutines.channels.Channel<MainIntent>(kotlinx.coroutines.channels.Channel.UNLIMITED)
    var state = mutableStateOf<MainState>(MainState.Idle)
        private set

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {collector ->
                when(collector) {
                    is MainIntent.FetchAnimals -> fetchAnimals()
                }

            }
        }
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            state.value = MainState.Loading
            state.value = try {
                MainState.Animals(repo.getAnimals())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage!!)
            }
        }
    }
}