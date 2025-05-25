package com.coinhub.android.data.use_cases

interface UseCase<InputT, OutputT> {
    suspend fun execute(input: InputT): OutputT
}