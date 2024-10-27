package co.uniandes.abcall.networking

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AbcallApi {

    @POST("chat/send-message")
    suspend fun sendMessage(@Body request: MessageRequest)

    @GET("issues_management")
    suspend fun getIssues(): Response<IssuesListResponse>

    @POST("issues_management")
    suspend fun createIssue(@Body request: IssueRequest): Response<IssueDataResponse>

    @POST("issues")
    suspend fun suggestIssue(@Body request: SuggestRequest): Response<SuggestResponse>

    @GET("user?scope=me")
    suspend fun getUser(): Response<UserDataResponse>

    @PATCH("user/{id}")
    suspend fun setUser(@Path("id") id: Int, @Body user: UserRequest): Response<UserDataResponse>

}
