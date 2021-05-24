package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.TrackModel
import com.anteeone.coverit.domain.usecases.domain.GetChartsUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.models.Container
import com.anteeone.coverit.ui.utils.models.SealedState
import javax.inject.Inject

class ChartsViewModel @Inject constructor(
    private val getChartsUsecase: GetChartsUsecase
): ViewModel() {

    sealed class TracksState{
        object Empty: TracksState()
        object Failure: TracksState()
        data class Success(val data: List<TrackModel>): TracksState()

        fun pack(isForSubscribers: Boolean) = Container(this,isForSubscribers)
    }

    val tracks: MutableLiveData<Container<TracksState>> =
        MutableLiveData(TracksState.Empty.pack(true))

    fun loadTracks(){
        getChartsUsecase.invoke(viewModelScope,Unit){
            when(it){
                is Outcome.Success -> {
                    tracks.postValue(TracksState.Success(it.data).pack(true))
                }
                is Outcome.Failure -> {
                    tracks.postValue(TracksState.Failure.pack(true))
                }
            }
        }
    }
}