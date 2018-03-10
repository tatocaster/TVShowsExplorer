package me.tatocaster.letinterview.features.movieslist.presentation

import dagger.Component
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.di.scope.ActivityScope
import me.tatocaster.letinterview.features.movieslist.usecase.MoviesListUseCaseModule

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MoviesListModule::class, MoviesListUseCaseModule::class])
interface MoviesListComponent {
    fun inject(view: MoviesList)

}