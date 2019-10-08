package com.moneam

import android.os.Bundle
import com.ahmoneam.basecleanarchitecture.Result
import com.ahmoneam.basecleanarchitecture.base.platform.BaseActivity
import com.ahmoneam.basecleanarchitecture.base.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        viewModel.getDate()
    }
}

class MainViewModel : BaseViewModel() {
    fun getDate() {
        wrapBlockingOperation {
            val result = getdata2()
            if (result is Result.Success) {
                Timber.e("Gooooooood")
            }
        }
    }

    suspend fun getdata2(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            Thread.sleep(3000)
            return@withContext Result.Success(true)
        }
    }

}


