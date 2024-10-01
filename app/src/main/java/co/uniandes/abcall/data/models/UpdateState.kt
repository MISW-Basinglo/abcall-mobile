package co.uniandes.abcall.data.models

sealed class UpdateState {
    data object Idle : UpdateState()
    data object Loading : UpdateState()
    data object Success : UpdateState()
    data class Error(val message: String) : UpdateState()
}
