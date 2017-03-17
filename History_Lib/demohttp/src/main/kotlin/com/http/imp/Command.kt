package com.http.imp

/**
 * Created by ZeroAries on 2016/2/19.
 */
interface Command <T> {
    fun execute(): T
}