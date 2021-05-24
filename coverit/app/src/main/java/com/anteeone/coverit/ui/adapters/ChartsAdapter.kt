package com.anteeone.coverit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.models.TrackModel

class ChartsAdapter(val onClick: (track: TrackModel) -> Unit) :
    RecyclerView.Adapter<ChartsAdapter.ChartsViewHolder>() {

    private var trackList: List<TrackModel> = emptyList()

    class ChartsViewHolder(itemView: View, val onClick: (track: TrackModel) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val mTitle: TextView = itemView.findViewById(R.id.item_charts_title)
        private val mArtist: TextView = itemView.findViewById(R.id.item_charts_artist)
        private val mPlayCount: TextView = itemView.findViewById(R.id.item_charts_playcount)

        fun bind(track: TrackModel) {
            mTitle.text = track.title
            mArtist.text = track.artist
            mPlayCount.text = track.playCount
            itemView.setOnClickListener {
                onClick(track)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartsViewHolder {
        return ChartsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.charts_list_item, parent, false), onClick
        )
    }

    override fun onBindViewHolder(holder: ChartsViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun setTrackModels(
        tracks: List<TrackModel>,
        onFinish: () -> Unit
    ) {
        this.trackList = tracks
        notifyDataSetChanged()
        onFinish()
    }
}