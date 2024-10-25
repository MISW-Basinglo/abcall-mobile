package co.uniandes.abcall.networking

import com.google.gson.annotations.SerializedName
import java.util.Date

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

data class SuggestRequest(
    @SerializedName("description") val description: String
)

data class IssueRequest(
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String,
    @SerializedName("source") val source: String
)

data class IssuesListResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: List<IssueResponse>
)

data class IssueDataResponse(
    @SerializedName("data") val data: IssueResponse
)

data class IssueResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: IssueType,
    @SerializedName("description") val description: String,
    @SerializedName("solution") val solution: String?,
    @SerializedName("status") val status: IssueStatus,
    @SerializedName("source") val source: IssueSource,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("company_id") val companyId: Int,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date?
)

enum class IssueSource {
    @SerializedName("CALL") CALL,
    @SerializedName("EMAIL") EMAIL,
    @SerializedName("APP_WEB") APP_WEB,
    @SerializedName("CHATBOT") CHATBOT,
    @SerializedName("APP_MOBILE") APP_MOBILE,
    @SerializedName("OTHER") OTHER;

    companion object {
        fun fromValue(value: String) = entries.find { it.name == value } ?: APP_MOBILE
    }
}

enum class IssueType {
    @SerializedName("REQUEST") REQUEST,
    @SerializedName("COMPLAINT") COMPLAINT,
    @SerializedName("CLAIM") CLAIM,
    @SerializedName("SUGGESTION") SUGGESTION,
    @SerializedName("PRAISE") PRAISE;

    companion object {
        fun fromValue(value: String): IssueType = entries.find { it.name == value } ?: REQUEST
    }
}

enum class IssueStatus {
    @SerializedName("OPEN") OPEN,
    @SerializedName("SCALED") SCALED,
    @SerializedName("CLOSED") CLOSED;

    companion object {
        fun fromValue(value: String) = entries.find { it.name == value } ?: OPEN
    }
}

data class SuggestResponse(
    @SerializedName("description") val description: String
)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String
)

data class UserRequest(
    @SerializedName("channel") val channel: String
)

data class UserDataResponse(
    @SerializedName("data") val data: UserResponse
)

data class UserResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("auth_id") val authId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("channel") val channel: UserChannel,
    @SerializedName("company_id") val companyId: Int,
    @SerializedName("dni") val dni: String,
    @SerializedName("email") val email: String,
    @SerializedName("importance") val importance: Int,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date?
)

enum class UserChannel {
    @SerializedName("EMAIL") EMAIL,
    @SerializedName("PHONE") PHONE;

    companion object {
        fun fromValue(value: String) = entries.find { it.name == value } ?: PHONE
    }
}
