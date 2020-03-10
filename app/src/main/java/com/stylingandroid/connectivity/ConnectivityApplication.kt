package com.stylingandroid.connectivity

import android.app.Application
import com.stylingandroid.connectivity.di.DaggerConnectivityApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ConnectivityApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerConnectivityApplicationComponent
            .factory()
            .create(this)
            .inject(this)
    }
}
