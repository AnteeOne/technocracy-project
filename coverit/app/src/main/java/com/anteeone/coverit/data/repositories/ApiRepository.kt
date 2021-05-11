package com.anteeone.coverit.data.repositories

import com.anteeone.coverit.data.network.ApiInterface
import com.anteeone.coverit.domain.models.TrackModel
import com.anteeone.coverit.domain.repositories.IApiRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiInterface: ApiInterface
): IApiRepository {

    override suspend fun getCharts(): List<TrackModel> =
        apiInterface.getCharts(30,1).convertToModel()
}