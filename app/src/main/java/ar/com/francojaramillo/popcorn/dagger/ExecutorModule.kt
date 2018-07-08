package ar.com.francojaramillo.popcorn.dagger

import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


/**
 * Module that provides an Executor to run async tasks
 */
@Module
class ExecutorModule {

    @Provides
    @Singleton
    fun provideBackgroundExecutor(): Executor {
        return Executors.newCachedThreadPool()
    }
}