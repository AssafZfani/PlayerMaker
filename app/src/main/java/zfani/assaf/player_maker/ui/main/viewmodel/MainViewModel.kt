package zfani.assaf.player_maker.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import zfani.assaf.player_maker.data.model.VersionTeam
import zfani.assaf.player_maker.data.repository.MainRepository
import zfani.assaf.player_maker.utils.Resource

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getVersionTeams() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = convertJsonToVersionTeams(mainRepository.getVersionTeams())))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun convertJsonToVersionTeams(response: JsonObject): List<VersionTeam> {
        if (!response.has("versionsListByTeams")) {
            return emptyList()
        }
        val versionTeamList = mutableListOf<VersionTeam>()
        val versionsListByTeams = response.getAsJsonObject("versionsListByTeams")
        versionsListByTeams.keySet().forEach { teamId ->
            val versionTeamJsonObject = versionsListByTeams.getAsJsonObject(teamId)
            versionTeamJsonObject.keySet().filterNot { it == "fotaEnabled" }
                .forEach { majorVersion ->
                    val versionJsonObject = versionTeamJsonObject.get(majorVersion).asJsonObject
                    versionTeamList.add(
                        VersionTeam(
                            teamId,
                            majorVersion,
                            versionTeamJsonObject.get("fotaEnabled").asBoolean,
                            versionJsonObject.get("version").asString,
                            versionJsonObject.get("binFile").asString,
                            versionJsonObject.get("jsonFile").asString
                        )
                    )
                }
        }
        return versionTeamList
    }
}