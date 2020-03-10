package com.stylingandroid.connectivity.di

import android.content.Context
import com.stylingandroid.connectivity.ConnectivityApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Scope
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivitiesModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface ConnectivityApplicationComponent {
    fun inject(app: ConnectivityApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ConnectivityApplicationComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
