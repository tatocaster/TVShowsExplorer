package me.tatocaster.letinterview

import me.tatocaster.letinterview.data.DataModule
import me.tatocaster.letinterview.features.movieslist.usecase.MoviesListUseCase
import me.tatocaster.letinterview.features.movieslist.usecase.MoviesListUseCaseImpl
import org.junit.Before
import org.junit.Test


class MoviesListUseCaseTest {
    private val NAME_OF_TEST_SHOW = "The Big Bang Theory"
    lateinit var useCase: MoviesListUseCase

    @Before
    fun setUp() {
        val dataModule = DataModule()
        useCase = MoviesListUseCaseImpl(dataModule.provideApi(dataModule.provideRetrofit()))
    }

    @Test
    fun testGetMoviesList() {
        useCase.getFromService(1)
                .test()
                .await()
                .assertNoErrors()
                .assertComplete()
                .assertValue { data -> data[0].name == NAME_OF_TEST_SHOW }
    }
}