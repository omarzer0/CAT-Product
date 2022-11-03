package az.zero.cat_product.core

import android.app.Application

class MyApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this.applicationContext)
    }

}