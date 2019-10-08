package com.ahmoneam.basecleanarchitecture.base.platform

import com.ahmoneam.basecleanarchitecture.Result
import com.ahmoneam.basecleanarchitecture.base.data.local.LocalDataSource
import com.ahmoneam.basecleanarchitecture.base.data.remote.RemoteDataSource
import com.ahmoneam.basecleanarchitecture.utils.ApplicationException
import com.ahmoneam.basecleanarchitecture.utils.ErrorType
import com.ahmoneam.basecleanarchitecture.utils.IConnectivityUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import retrofit2.Response

abstract class BaseRepository<LocalData : LocalDataSource, RemoteData : RemoteDataSource> :
    IBaseRepository {

    val connectivityUtils: IConnectivityUtils by inject()

    override suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                // check internet connection
                if (connectivityUtils.isNetworkConnected.not())
                    return@withContext Result.Error(
                        ApplicationException(type = ErrorType.Network.NoInternetConnection)
                    )

                // make api call
                val response = call.invoke()

                // check response and convert to result
                return@withContext when (response.code()) {
                    in 1..399 -> Result.Success(response.body()!!)
                    401 -> Result.Error(ApplicationException(type = ErrorType.Network.Unauthorized))
                    404 -> Result.Error(ApplicationException(type = ErrorType.Network.ResourceNotFound))
                    else -> Result.Error(ApplicationException(type = ErrorType.Network.Unexpected))
                }
            } catch (error: Throwable) {
                Result.Error(
                    ApplicationException(
                        throwable = error,
                        type = ErrorType.Network.Unexpected
                    )
                )
            }
        }
    }

}