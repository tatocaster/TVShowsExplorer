package me.tatocaster.letinterview

import me.tatocaster.letinterview.data.DataModule
import me.tatocaster.letinterview.features.moviesdetail.domain.usecase.MoviesDetailUseCase
import me.tatocaster.letinterview.features.moviesdetail.domain.usecase.MoviesDetailUseCaseImpl
import org.junit.Before
import org.junit.Test

class MoviesDetailUseCaseTest {
    lateinit var useCase: MoviesDetailUseCase

    @Before
    fun setUp() {
        val dataModule = DataModule()
        useCase = MoviesDetailUseCaseImpl(dataModule.provideApi(dataModule.provideRetrofit()))
    }

    @Test
    fun testGetSimilarShows() {
        useCase.getSimilarShows(1, 1)
                .test()
                .await()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun testGetTvData() {
        useCase.getTvData(1)
                .test()
                .await()
                .assertNoErrors()
                .assertComplete()
                .assertValue { it.overview.isNotBlank() }
    }
}