package com.shakir.spliff.utils

interface DataFetchCallback <T> {
    fun onSuccess(data : T)
    fun onError(throwable: Throwable)
}