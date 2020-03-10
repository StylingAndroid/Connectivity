package com.stylingandroid.connectivity.di

import com.stylingandroid.connectivity.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributesMainActivityInjector(): MainActivity
}
