package com.rkpattanaik.social.core.retrofit.factory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

object FlowAdapterFactorySettings {
    var dispatcher = Dispatchers.IO
    var isAsyncByDefault = true
    val syncDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
}