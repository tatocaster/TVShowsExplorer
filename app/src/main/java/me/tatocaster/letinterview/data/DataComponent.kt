package me.tatocaster.letinterview.data

import me.tatocaster.letinterview.data.api.ApiService


interface DataComponent {
    fun exposeApiService(): ApiService
}