package com.anteeone.coverit.domain.usecases.domain

import android.graphics.Bitmap
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IFireStorageRepository
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import java.lang.Exception
import javax.inject.Inject

class UploadAvatarUsecase @Inject constructor(
    private val usersRepository: IUsersRepository,
    private val fireStorageRepository: IFireStorageRepository
): Usecase<Unit, UploadAvatarUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Unit> {
        try {
            usersRepository.updateCurrentUser(
                User(
                    avatarUri = fireStorageRepository.updateCurrentUserAvatar(params.imageBitmap)
                )
            )
            return Outcome.Success(Unit)
        }
        catch (ex:Exception){
            return Outcome.Failure(ex)
        }

    }

    data class Params(val imageBitmap: Bitmap)
}