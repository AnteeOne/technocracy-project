package com.anteeone.coverit.domain.usecases.base

import com.anteeone.coverit.domain.utils.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class Usecase<out Type,in Params>{

    abstract suspend fun run(params: Params):Outcome<Type>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Outcome<Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async { run(params) }
        scope.launch { onResult(backgroundJob.await())}
    }

}