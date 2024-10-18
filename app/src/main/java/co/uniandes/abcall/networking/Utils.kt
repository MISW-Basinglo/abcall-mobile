package co.uniandes.abcall.networking

import com.auth0.android.jwt.JWT

object Utils {

    const val ROLE_CLAIM = "role"

    fun getJwtClaim(token: String, claim: String): String? {
        val jwt = JWT(token)
        return jwt.getClaim(claim).asString()
    }

}

enum class Roles(val value: String) {
    USER("user")
}
