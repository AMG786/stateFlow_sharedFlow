package com.example.flowproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class viewModel() : ViewModel() {

    private val _stateFlow = MutableStateFlow(0);  // Mutable State Flow

    val stateFlow = _stateFlow.asStateFlow();  // Immutable State Flow

    fun incrementCounter(){  // changes the state
        _stateFlow.value +=1;
    }

    private val _sharedFlow = MutableSharedFlow<Int>() // Mutable Share Flow

    val sharedFlow = _sharedFlow.asSharedFlow()  // Immutable Share Flow

    fun squareNumber(number:Int){
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }


    init {
        viewModelScope.launch {
            sharedFlow.collect {
                delay(2000L)
                println("First Flow : The received Number is $it")
            }
        }
        viewModelScope.launch {
            sharedFlow.collect {
                delay(3000L)
                println("Secound Flow : The received Number is $it")
            }
        }

        squareNumber(3)

    }


}