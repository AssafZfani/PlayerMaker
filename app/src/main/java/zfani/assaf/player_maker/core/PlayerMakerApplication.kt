package zfani.assaf.player_maker.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import zfani.assaf.player_maker.data.api.RetrofitBuilder
import zfani.assaf.player_maker.data.repository.MainRepository
import zfani.assaf.player_maker.ui.main.viewmodel.MainViewModel
import zfani.assaf.player_maker.ui.version_team.viemodel.VersionTeamViewModel

class PlayerMakerApplication : Application() {

    private var moduleList = module {
        viewModel { MainViewModel(get()) }
        viewModel { VersionTeamViewModel(this@PlayerMakerApplication) }
        single { MainRepository(RetrofitBuilder.apiService) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PlayerMakerApplication)
            modules(moduleList)
        }
    }
}