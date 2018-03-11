package me.tatocaster.letinterview.features.movieslist.usecase

import dagger.Module
import dagger.Provides

@Module
class MoviesListUseCaseModule {
    @Provides
    fun provideGetAllAvailableProviders(usecase: MoviesListUseCaseImpl): MoviesListUseCase = usecase
}