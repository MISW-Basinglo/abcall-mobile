package co.uniandes.abcall.ui.screens.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.auth.AuthRepository
import co.uniandes.abcall.data.repositories.user.UserRepository
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.UserRequest
import co.uniandes.abcall.networking.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel  @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> get() = _updateState

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> get() = _user

    fun getUser() {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                when (val result = userRepository.getUser()) {
                    is Result.Success -> {
                        _updateState.value = UpdateState.Idle
                        _user.value = result.data
                    }
                    is Result.Error -> _updateState.value = UpdateState.Error(result.message)
                }
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun setUser(channel: String) {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                _user.value?.let {
                    val user = UserRequest(it.name, it.phone, channel, it.email)
                    when (val result = userRepository.setUser(it.id, user)
                    ) {
                        is Result.Success -> {
                            _user.value = result.data
                            _updateState.value = UpdateState.Success
                        }
                        is Result.Error -> _updateState.value = UpdateState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun resetState() {
        _updateState.value = UpdateState.Idle
    }

}
