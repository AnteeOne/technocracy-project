package com.anteeone.coverit.domain.repositories

import android.graphics.Bitmap

interface IFireStorageRepository {

    suspend fun updateCurrentUserAvatar(bitmap: Bitmap): String

}