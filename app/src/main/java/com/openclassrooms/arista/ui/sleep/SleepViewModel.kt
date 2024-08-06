package com.openclassrooms.arista.ui.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(private val getAllSleepsUseCase: GetAllSleepsUseCase) :
    ViewModel() {
    private val _sleepsFlow = MutableStateFlow<List<Sleep>>(emptyList())
    val sleepsFlow: StateFlow<List<Sleep>> = _sleepsFlow.asStateFlow()

    init {
        fetchSleeps()
    }

    fun fetchSleeps() {
        viewModelScope.launch(Dispatchers.IO) {
            val sleepList = getAllSleepsUseCase.execute()
            _sleepsFlow.value = sleepList
        }
    }

}
