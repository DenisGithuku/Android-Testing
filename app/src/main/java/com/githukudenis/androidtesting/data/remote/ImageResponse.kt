package com.githukudenis.androidtesting.data.remote

data class ImageResponse (
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
        )
