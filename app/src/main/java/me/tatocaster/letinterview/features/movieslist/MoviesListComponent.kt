package me.tatocaster.letinterview.features.movieslist

import dagger.Component
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.di.scope.ActivityScope

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(MoviesListModule::class)])
interface MoviesListComponent {
    fun inject(view: MoviesList)

}