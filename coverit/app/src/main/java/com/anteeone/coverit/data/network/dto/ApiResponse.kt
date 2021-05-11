package com.anteeone.coverit.data.network.dto


import com.anteeone.coverit.domain.models.TrackModel
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("tracks")
    val tracks: Tracks
) {
    data class Tracks(
        @SerializedName("@attr")
        val attr: Attr,
        @SerializedName("track")
        val track: List<Track>
    ) {
        data class Attr(
            @SerializedName("page")
            val page: String,
            @SerializedName("perPage")
            val perPage: String,
            @SerializedName("total")
            val total: String,
            @SerializedName("totalPages")
            val totalPages: String
        )

        data class Track(
            @SerializedName("artist")
            val artist: Artist,
            @SerializedName("duration")
            val duration: String,
            @SerializedName("image")
            val image: List<Image>,
            @SerializedName("listeners")
            val listeners: String,
            @SerializedName("mbid")
            val mbid: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("playcount")
            val playcount: String,
            @SerializedName("streamable")
            val streamable: Streamable,
            @SerializedName("url")
            val url: String
        ) {
            data class Artist(
                @SerializedName("mbid")
                val mbid: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("url")
                val url: String
            )

            data class Image(
                @SerializedName("size")
                val size: String,
                @SerializedName("#text")
                val text: String
            )

            data class Streamable(
                @SerializedName("fulltrack")
                val fulltrack: String,
                @SerializedName("#text")
                val text: String
            )

            fun toTrackModel() = TrackModel(
                name,
                artist.name,
                playcount,
                url
            )

        }
    }

    fun convertToModel(): List<TrackModel> =
        this.tracks.track.map { x -> x.toTrackModel() }
}