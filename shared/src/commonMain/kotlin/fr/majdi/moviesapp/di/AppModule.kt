package fr.majdi.moviesapp.di

import fr.majdi.moviesapp.data.remote.ApiService
import fr.majdi.moviesapp.data.remote.ApiServiceImpl
import fr.majdi.moviesapp.data.repository.MovieRepositoryImpl
import fr.majdi.moviesapp.domain.repository.MovieRepository
import fr.majdi.moviesapp.domain.usecase.GetNowPlayingMoviesUseCase
import fr.majdi.moviesapp.presentation.viewmodel.MovieDetailViewModel
import fr.majdi.moviesapp.presentation.viewmodel.MovieListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::httpClientModule)
    singleOf(::ApiServiceImpl) bind ApiService::class
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
    factoryOf(::GetNowPlayingMoviesUseCase)
    viewModel { MovieListViewModel(get()) }
    viewModel { params ->
        MovieDetailViewModel(
            getNowPlayingMoviesUseCase = get(),
            savedStateHandle = params.get()
        )
    }
}
