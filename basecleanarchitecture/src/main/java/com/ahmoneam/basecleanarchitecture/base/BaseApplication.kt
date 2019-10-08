package com.ahmoneam.basecleanarchitecture.base

import android.app.Application
import androidx.annotation.CallSuper
import com.ahmoneam.basecleanarchitecture.base.di.KoinModules
import com.ahmoneam.basecleanarchitecture.utils.ReleaseTree
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.MESSAGE
import timber.log.Timber

abstract class BaseApplication : Application() {

    abstract val interceptors: List<Interceptor>
    abstract val baseUrl: String
    abstract val sharedPreferencesName: String
    abstract val isDebug: Boolean
    lateinit var koin: KoinApplication
        private set

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        if (isDebug) {
            Logger.addLogAdapter(AndroidLogAdapter())

            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }

        koin = startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@BaseApplication)
            modules(
                KoinModules.initApplicationModule(
                    baseUrl,
                    sharedPreferencesName,
                    isDebug,
                    interceptors
                )
            )
        }

        koin.logger(object : org.koin.core.logger.Logger() {
            override fun log(level: Level, msg: MESSAGE) {
                when (level) {
                    Level.DEBUG -> Timber.d(msg)
                    Level.INFO -> Timber.i(msg)
                    Level.ERROR -> Timber.e(msg)
                }
            }
        })
    }
}