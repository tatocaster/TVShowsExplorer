package me.tatocaster.letinterview.features.moviesdetail.presentation

import dagger.Component
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.di.scope.ActivityScope
import me.tatocaster.letinterview.features.moviesdetail.domain.usecase.MoviesDetailUseCaseModule

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MoviesDetailModule::class, MoviesDetailUseCaseModule::class])
interface MoviesDetailComponent {
    fun inject(view: MoviesDetail)

}