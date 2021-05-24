package com.anteeone.coverit.domain.repositories

import com.anteeone.coverit.domain.models.TrackModel

interface IApiRepository {
    suspend fun getCharts(): List<TrackModel>
}