package com.example.submission2_ezpz.data

import com.google.gson.annotations.SerializedName

data class SearchResult(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<User?>? = null
)


