package co.uniandes.abcall.di

import android.content.Context
import co.uniandes.abcall.storage.LocalStorage
import co.uniandes.abcall.storage.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun providesSharedPreferencesManager(@ApplicationContext context: Context): LocalStorage {
        return SharedPreferencesManager(context)
    }

}
