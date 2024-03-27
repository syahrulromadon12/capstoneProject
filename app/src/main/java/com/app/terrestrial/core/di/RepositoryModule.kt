package com.app.terrestrial.core.di

import com.app.terrestrial.core.data.TerrestrialRepository
import com.app.terrestrial.core.domain.repository.ITerrestrialRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(tourismRepository: TerrestrialRepository): ITerrestrialRepository

}