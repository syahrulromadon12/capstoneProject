package com.app.terrestrial.di

import com.app.terrestrial.core.domain.usecase.TerrestrialInteractor
import com.app.terrestrial.core.domain.usecase.TerrestrialUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTerrestrialUseCase(tourismInteractor: TerrestrialInteractor): TerrestrialUseCase {
        return tourismInteractor
    }
}

