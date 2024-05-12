package com.choice.listonview.core

import androidx.annotation.Keep

@Keep
sealed class IResult<out DTO> {

    data class OnSuccess<out DTO>(val response: DTO) : IResult<DTO>()

    data class OnFailed(val exceptionError: Throwable) : IResult<Nothing>(){
        val message = exceptionError.message
    }

    data class OnLoading(val message: String) : IResult<Nothing>()


    companion object {

        fun <OUT> success(data: OUT) = OnSuccess(data)

        fun failed(exceptionError: Throwable) = OnFailed(exceptionError)

        fun loading(message: String = "Waiting...") = OnLoading(message)

        suspend fun <DTO> IResult<DTO>.onSuccess(block: suspend (DTO) -> Unit) = apply {
            if(this is OnSuccess){
                block(this.response)
            }
        }

        suspend fun <DTO> IResult<DTO>.onFailed(block: suspend (exception: Throwable) -> Unit) = apply {
            if(this is OnFailed){
                block(this.exceptionError)
            }
        }

        suspend fun <DTO> IResult<DTO>.onLoading(block: suspend (message: String) -> Unit) = apply {
            if(this is OnLoading){
                block(this.message)
            }
        }

    }
}