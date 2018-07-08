package ar.com.francojaramillo.popcorn.dagger

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent