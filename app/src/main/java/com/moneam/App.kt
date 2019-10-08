package com.moneam

import com.ahmoneam.basecleanarchitecture.base.BaseApplication
import okhttp3.Interceptor
import org.koin.dsl.module

class App : BaseApplication() {
    override val baseUrl = "base"
    override val sharedPreferencesName = "test"
    override val isDebug = true
    override val interceptors = emptyList<Interceptor>()

    override fun onCreate() {
        super.onCreate()
        koin.modules(module {
            factory { MainViewModel() }
        })
    }
}