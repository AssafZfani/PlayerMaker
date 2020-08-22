package zfani.assaf.player_maker.ui.version_team.viemodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import zfani.assaf.player_maker.data.model.VersionTeam
import zfani.assaf.player_maker.utils.Resource
import java.io.FileWriter

class VersionTeamViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val FILE_NAME = "/FotaSettings-Assaf_Zfani.json"
    }

    fun saveJsonToLocalStorage(versionTeam: VersionTeam) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        val fileWriter = FileWriter(getApplication<Application>().filesDir.absolutePath + FILE_NAME)
        try {
            fileWriter.write(convertVersionTeamsToJson(versionTeam).toString())
            emit(Resource.success(data = true))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        } finally {
            fileWriter.flush()
            fileWriter.close()
        }
    }

    private fun convertVersionTeamsToJson(versionTeam: VersionTeam): JSONObject {
        return JSONObject().put(versionTeam.teamId, JSONObject().run {
            put("fotaEnabled", versionTeam.fotaEnabled)
            put(versionTeam.majorVersion, JSONObject().run {
                put("version", versionTeam.version)
                put("binFile", versionTeam.binFile)
                put("jsonFile", versionTeam.jsonFile)
            })
        })
    }
}