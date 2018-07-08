package ar.com.francojaramillo.popcorn.dagger

import ar.com.francojaramillo.popcorn.ui.fragments.FavoritesMoviesFragment
import ar.com.francojaramillo.popcorn.ui.fragments.MoviesFragment
import ar.com.francojaramillo.popcorn.ui.fragments.SearchFragment
import ar.com.francojaramillo.popcorn.viewmodels.ViewModelFactory
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ExecutorModule::class,
                             RepositoryModule::class, ViewModelModule::class, RoomModule::class))
interface AppComponent {
    fun inject(searchFragment: SearchFragment)
    fun inject(moviesFragment: MoviesFragment)
    fun inject(moviesFragment: FavoritesMoviesFragment)
}