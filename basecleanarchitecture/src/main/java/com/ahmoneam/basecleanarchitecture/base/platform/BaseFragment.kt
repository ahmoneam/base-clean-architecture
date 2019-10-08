package com.ahmoneam.basecleanarchitecture.base.platform

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ahmoneam.basecleanarchitecture.Result
import com.ahmoneam.basecleanarchitecture.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseFragment<ViewModel : BaseViewModel>
    : Fragment() {

    val viewModel: ViewModel by lazy { getViewModel(viewModelClass()) }

    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<ViewModel> {
        // dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<ViewModel>).kotlin
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loading.observe(this, EventObserver {
            if (it.loading) showLoading()
            else hideLoading()
        })

        viewModel.error.observe(this, Observer {
            hideLoading()
            showError(it)
        })
    }

    open fun showError(error: Result.Error) {
        TODO()
    }

    open fun hideLoading() {
        TODO()
    }

    open fun showLoading() {
        TODO()
    }

}