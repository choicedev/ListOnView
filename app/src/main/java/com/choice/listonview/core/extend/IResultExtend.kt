package com.choice.listonview.core.extend

import com.choice.listonview.core.IResult

fun <DTO> IResult<DTO>.watchStatus(
    success: ((DTO) -> Unit)? = null,
    failure: ((Throwable) -> Unit)? = null,
    loading: ((String) -> Unit)? = null
){
    when(this){
        is IResult.OnSuccess -> success?.invoke(this.response)
        is IResult.OnFailed -> failure?.invoke(this.exceptionError)
        is IResult.OnLoading -> loading?.invoke(this.message)
    }
}