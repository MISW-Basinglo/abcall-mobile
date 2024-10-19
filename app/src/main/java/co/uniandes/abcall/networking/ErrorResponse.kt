package co.uniandes.abcall.networking

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("msg") val message: String,
    @SerializedName("status") val status: String
)

object ServerErrorMessage {
    const val USER_NOT_AUTHORIZED = "User not authorized."
    const val USER_NOT_REGISTERED = "User not registered."
    const val USER_INVALID_PASSWORD = "Invalid password."
    const val USER_NOT_FOUND = "User not found."
    const val GENERIC_ERROR = "General error."
}
