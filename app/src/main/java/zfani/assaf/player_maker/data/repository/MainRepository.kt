package zfani.assaf.player_maker.data.repository

import zfani.assaf.player_maker.data.api.ApiService

class MainRepository(private val apiService: ApiService) {

    suspend fun getVersionTeams() = apiService.getVersionTeams()
}