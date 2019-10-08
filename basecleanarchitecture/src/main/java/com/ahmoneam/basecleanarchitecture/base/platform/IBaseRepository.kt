package com.ahmoneam.basecleanarchitecture.base.platform

import com.ahmoneam.basecleanarchitecture.Result
import org.koin.core.KoinComponent
import retrofit2.Response

interface IBaseRepository :
    KoinComponent {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T>
}