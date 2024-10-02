package co.uniandes.abcall.ui.screens.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel  @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> get() = _updateState

    fun updateChannel(channel: String) {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                delay(2000)
                repository.updateChannel(channel)
                _updateState.value = UpdateState.Success
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error("Error de red: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun resetState() {
        _updateState.value = UpdateState.Idle
    }

}
