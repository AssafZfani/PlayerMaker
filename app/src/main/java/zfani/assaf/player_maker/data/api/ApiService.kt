package zfani.assaf.player_maker.data.api

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {

    @GET("FotaSettings.json")
    suspend fun getVersionTeams(): JsonObject
}