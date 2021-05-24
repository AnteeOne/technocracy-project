package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.data.repositories.ApiRepository
import com.anteeone.coverit.domain.models.TrackModel
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import java.lang.Exception
import javax.inject.Inject

class GetChartsUsecase @Inject constructor(
    private val apiRepository: ApiRepository
) : Usecase<List<TrackModel>,Unit>() {

    override suspend fun run(params: Unit): Outcome<List<TrackModel>> {
        try {
            return Outcome.Success(apiRepository.getCharts())
        } catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

}