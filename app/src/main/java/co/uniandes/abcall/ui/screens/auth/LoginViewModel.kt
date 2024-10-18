package co.uniandes.abcall.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.auth.AuthRepository
import co.uniandes.abcall.networking.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel()  {

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> get() = _updateState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                when (val result = repository.login(email, password)) {
                    is Result.Success -> _updateState.value = UpdateState.Success
                    is Result.Error -> _updateState.value = UpdateState.Error(result.message)
                }
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun resetState() {
        _updateState.value = UpdateState.Idle
    }

}
