package com.choice.listonview.core

interface UseCase<IN, OUT> {

    suspend operator fun invoke(input: IN): OUT

}