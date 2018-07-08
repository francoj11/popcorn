package ar.com.francojaramillo.popcorn

import android.app.Application
import ar.com.francojaramillo.popcorn.dagger.AppComponent
import ar.com.francojaramillo.popcorn.dagger.AppModule
import ar.com.francojaramillo.popcorn.dagger.DaggerAppComponent
import ar.com.francojaramillo.popcorn.dagger.RoomModule

class PopcornApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = initDagger(this)
    }

    private fun initDagger(app: PopcornApplication): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(app)).roomModule(RoomModule(app)).build()
    }
}