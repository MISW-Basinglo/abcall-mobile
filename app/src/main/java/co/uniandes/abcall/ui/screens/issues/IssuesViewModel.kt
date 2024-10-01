package co.uniandes.abcall.ui.screens.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.Issue
import co.uniandes.abcall.data.models.UpdateState
import co.uniandes.abcall.data.repositories.issues.IssuesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val repository: IssuesRepository
) : ViewModel() {

    private val _issues = MutableLiveData<List<Issue>>()
    val issues: LiveData<List<Issue>> get() = _issues

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> get() = _updateState

    init {
        loadIssues()
    }

    private fun loadIssues() {
        viewModelScope.launch {
            try {
                _updateState.value = UpdateState.Loading
                delay(2000)
                _issues.value = repository.getIssues()
                _updateState.value = UpdateState.Success
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error("Error de red: ${e.message}")
            }
        }
    }

    fun resetState() {
        _updateState.value = UpdateState.Idle
    }

}

