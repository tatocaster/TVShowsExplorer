package me.tatocaster.letinterview.features.movieslist.domain.usecase

import dagger.Module
import dagger.Provides

@Module
class MoviesListUseCaseModule {
    @Provides
    fun provideGetAllAvailableProviders(usecase: MoviesListUseCaseImpl): MoviesListUseCase = usecase
}