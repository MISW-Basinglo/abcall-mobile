package co.uniandes.abcall.di

import co.uniandes.abcall.data.repositories.auth.AuthRepository
import co.uniandes.abcall.data.repositories.auth.AuthRepositoryImpl
import co.uniandes.abcall.data.repositories.chat.ChatRepository
import co.uniandes.abcall.data.repositories.chat.ChatRepositoryImpl
import co.uniandes.abcall.data.repositories.issues.IssuesRepository
import co.uniandes.abcall.data.repositories.issues.IssuesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindIssuesRepository(
        issuesRepositoryImpl: IssuesRepositoryImpl
    ): IssuesRepository

    @Binds
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}
