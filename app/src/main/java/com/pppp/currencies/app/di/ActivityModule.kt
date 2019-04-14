package com.pppp.currencies.app.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val appCompatActivity: AppCompatActivity) {
    @Provides
    fun provideActivity(): AppCompatActivity = appCompatActivity
}
