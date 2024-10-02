package co.uniandes.abcall.data.models

sealed class IAState {
    data object Idle : IAState()
    data object Loading : IAState()
    data object Success : IAState()
    data class Error(val message: String) : IAState()
}
