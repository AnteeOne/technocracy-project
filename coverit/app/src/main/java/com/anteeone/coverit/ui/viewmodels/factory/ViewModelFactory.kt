package com.anteeone.coverit.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val map: Map<
            Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val mapMember = map[modelClass] ?:
            map.asIterable().firstOrNull() {modelClass.isAssignableFrom(it.key)}?.value ?:
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")

        return try {
            mapMember.get() as T
        } catch (ex: Exception){
            throw  RuntimeException(ex)
        }
    }


}
