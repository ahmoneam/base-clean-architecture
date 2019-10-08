package com.ahmoneam.basecleanarchitecture.base.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import kotlin.reflect.KClass

class SharedPreferencesUtils constructor(
    private val gson: Gson,
    private val context: Context,
    private val sharedPreferencesName: String
) :
    SharedPreferencesInterface {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val mPrefs: SharedPreferences = EncryptedSharedPreferences
        .create(
            sharedPreferencesName,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    override fun putString(key: String, value: String) {
        mPrefs.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return mPrefs.getString(key, null)
    }

    override fun <T> putObject(key: String, value: T) {
        mPrefs.edit().putString(key, gson.toJson(value)).apply()
    }

    override fun <T : Any> getObject(key: String, type: KClass<T>): T? {
        val s = mPrefs.getString(key, null) ?: return null
        return gson.fromJson(s, type.java)
    }

    override fun clearData() {
        mPrefs.edit().clear().apply()
    }
}