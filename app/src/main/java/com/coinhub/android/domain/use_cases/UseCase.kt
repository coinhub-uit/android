package com.coinhub.android.domain.use_cases

interface UseCase<InputT, OutputT> {
    suspend operator fun invoke(input: InputT): OutputT
}