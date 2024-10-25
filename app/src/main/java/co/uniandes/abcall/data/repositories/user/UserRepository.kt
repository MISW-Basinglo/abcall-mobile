package co.uniandes.abcall.data.repositories.user

import co.uniandes.abcall.networking.Result
import co.uniandes.abcall.networking.UserRequest
import co.uniandes.abcall.networking.UserResponse

interface UserRepository {

    suspend fun getUser(): Result<UserResponse>

    suspend fun setUser(user: UserRequest): Result<UserResponse>

}