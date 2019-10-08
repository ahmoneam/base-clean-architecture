package com.ahmoneam.basecleanarchitecture.base.data.local

import kotlin.reflect.KClass

interface SharedPreferencesInterface {
    fun clearData()
    fun getString(key: String): String?
    fun putString(key: String, value: String)
    fun <T> putObject(key: String, value: T)
    fun <T : Any> getObject(key: String, type: KClass<T>): T?
}