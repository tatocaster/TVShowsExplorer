package me.tatocaster.letinterview.features.moviesdetail.usecase

import dagger.Module
import dagger.Provides

@Module
class MoviesDetailUseCaseModule {
    @Provides
    fun provideGetAllAvailableProviders(usecase: MoviesDetailUseCaseImpl): MoviesDetailUseCase = usecase
}