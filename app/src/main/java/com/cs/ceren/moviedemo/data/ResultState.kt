package com.cs.ceren.moviedemo.data

sealed class ResultState <out T : Any> {
    class Success<out T : Any>(val data: T) : ResultState<T>()

    class Error(val exception: Throwable) : ResultState<Nothing>()
}