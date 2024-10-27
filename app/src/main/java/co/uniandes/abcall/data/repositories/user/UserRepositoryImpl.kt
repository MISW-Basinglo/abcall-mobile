package co.uniandes.abcall.data.repositories.user

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.ErrorResponse
import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.ServerErrorMessage
import co.uniandes.abcall.networking.UserRequest
import co.uniandes.abcall.networking.UserResponse
import com.google.gson.Gson
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: AbcallApi
): UserRepository {

    override suspend fun getUser(): Result<UserResponse> {
        val response = api.getUser()
        return if (response.isSuccessful) {
            response.body()?.let { userResponse ->
                return Result.Success(userResponse.data)
            } ?: Result.Error(ServerErrorMessage.GENERIC_ERROR)
        } else {
            val gson = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.Error(gson.message)
        }
    }

    override suspend fun setUser(userId: Int, user: UserRequest): Result<UserResponse> {
        val response = api.setUser(userId, user)
        return if (response.isSuccessful) {
            response.body()?.let { userResponse ->
                return Result.Success(userResponse.data)
            } ?: Result.Error(ServerErrorMessage.GENERIC_ERROR)
        } else {
            val gson = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            Result.Error(gson.message)
        }
    }

}