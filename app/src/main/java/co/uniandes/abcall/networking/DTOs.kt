package co.uniandes.abcall.networking

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class ChannelUpdateRequest(
    @SerializedName("channel") val channel: String
)

data class MessageRequest(
    @SerializedName("message") val message: String
)

data class IssueRequest(
    @SerializedName("issue_type") val type: String,
    @SerializedName("issue_description") val description: String
)

data class IssueResponse(
    @SerializedName("issue_type") val type: String,
    @SerializedName("issue_description") val description: String
)
