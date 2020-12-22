package io.github.shakilbinkarim.pexelimagesearchapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.shakilbinkarim.pexelimagesearchapp.repository.PexelApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePexelApiApi(): PexelApi =
        Retrofit.Builder()
            .baseUrl(PexelApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelApi::class.java)

}