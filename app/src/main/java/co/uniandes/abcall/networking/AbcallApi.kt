package co.uniandes.abcall.networking

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AbcallApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @PUT("user/update-channel")
    suspend fun updateChannel(@Body request: ChannelUpdateRequest)

    @POST("chat/send-message")
    suspend fun sendMessage(@Body request: MessageRequest)

    @GET("issues")
    suspend fun getIssues(): Response<List<IssueResponse>>

    @POST("issues")
    suspend fun createIssue(@Body request: IssueRequest)

    @POST("issues")
    suspend fun suggestIssue(@Body request: SuggestRequest): Response<SuggestResponse>

}
