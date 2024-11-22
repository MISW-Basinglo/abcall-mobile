package co.uniandes.abcall.ui.screens.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.issues.IssuesRepository
import co.uniandes.abcall.data.repositories.user.UserRepository
import co.uniandes.abcall.networking.IssueResponse
import co.uniandes.abcall.networking.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val repository: IssuesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _issues = MutableLiveData<List<IssueResponse>>()
    val issues: LiveData<List<IssueResponse>> get() = _issues

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> get() = _updateState

    fun loadIssues() {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                when (val userResult = userRepository.getUser()){
                    is Result.Success -> {
                        when (val result = repository.getIssues(userResult.data.id)) {
                            is Result.Success -> {
                                _updateState.value = UpdateState.Success
                                _issues.value = result.data.sortedByDescending { it.createdAt }
                            }
                            is Result.Error -> _updateState.value = UpdateState.Error(result.message)
                        }
                    }
                    is Result.Error -> _updateState.value = UpdateState.Error(userResult.message)
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

