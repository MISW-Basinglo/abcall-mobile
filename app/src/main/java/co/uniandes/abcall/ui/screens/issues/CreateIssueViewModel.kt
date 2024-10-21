package co.uniandes.abcall.ui.screens.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.IAState
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.issues.IssuesRepository
import co.uniandes.abcall.networking.IssueType
import co.uniandes.abcall.networking.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateIssueViewModel @Inject constructor(
    private val repository: IssuesRepository
) : ViewModel() {

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> get() = _updateState


    private val _iaState = MutableLiveData<IAState>(IAState.Idle)
    val iaState: LiveData<IAState> get() = _iaState


    private val _suggestState = MutableLiveData<String>()
    val suggestState: LiveData<String> get() = _suggestState

    fun createIssue(type: String, description: String) {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                when (val result = repository.createIssue(type, description)) {
                    is Result.Success -> {
                        _updateState.value = UpdateState.Success
                    }
                    is Result.Error -> _updateState.value = UpdateState.Error(result.message)
                }
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun suggestIssue(description: String) {
        viewModelScope.launch {
            try {
                _iaState.value = IAState.Loading
                delay(2000)
                _suggestState.value = repository.suggestIssue(description)
                _iaState.value = IAState.Success
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun resetState() {
        _iaState.value = IAState.Idle
        _updateState.value = UpdateState.Idle
    }

}

