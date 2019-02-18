package com.teamnexters.android.mealdiary.data.remote.response

import com.google.gson.annotations.SerializedName

internal data class SearchResponse(
        val documents: List<Document>
)

internal data class Document(
        @SerializedName("place_name")
        val placeName: String,
        @SerializedName("address_name")
        val addressName: String,
        val x: String,
        val y: String
)