package com.rkpattanaik.social.core.retrofit.factory

import kotlinx.coroutines.Dispatchers

object FlowAdapterFactorySettings {
    var dispatcher = Dispatchers.IO
    var isAsyncByDefault = true
}