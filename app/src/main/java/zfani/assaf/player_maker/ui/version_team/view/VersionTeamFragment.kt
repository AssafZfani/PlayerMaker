package zfani.assaf.player_maker.ui.version_team.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_version_team.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import zfani.assaf.player_maker.R
import zfani.assaf.player_maker.data.model.VersionTeam
import zfani.assaf.player_maker.ui.version_team.viemodel.VersionTeamViewModel
import zfani.assaf.player_maker.utils.Status

class VersionTeamFragment : Fragment() {

    companion object {
        private const val KEY_VERSION_TEAM = "version_team"

        fun newInstance(versionTeam: VersionTeam): VersionTeamFragment {
            val fragment = VersionTeamFragment()
            val args = Bundle().apply {
                putParcelable(KEY_VERSION_TEAM, versionTeam)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel by viewModel<VersionTeamViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_version_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindData()
        setupUI()
    }

    private fun bindData() {
        arguments?.getParcelable<VersionTeam>(KEY_VERSION_TEAM)?.let {
            tvTeamId.text = it.teamId
            tilMajorVersion.editText?.setText(it.majorVersion)
            tilVersion.editText?.setText(it.version)
            tilBinFile.editText?.setText(it.binFile)
            tilJsonFile.editText?.setText(it.jsonFile)
            scFotaEnabled.isChecked = it.fotaEnabled
        }
    }

    private fun setupUI() {
        btnSaveChanges.setOnClickListener {
            viewModel.saveJsonToLocalStorage(
                VersionTeam(
                    tvTeamId.text.toString(),
                    tilMajorVersion.editText?.text.toString(),
                    scFotaEnabled.isChecked,
                    tilVersion.editText?.text.toString(),
                    tilBinFile.editText?.text.toString(),
                    tilJsonFile.editText?.text.toString()
                )
            ).observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> Toast.makeText(
                        activity,
                        R.string.file_write_succeeded,
                        Toast.LENGTH_LONG
                    ).show()
                    Status.ERROR -> {
                        Toast.makeText(
                            activity,
                            getString(R.string.file_write_failed) + " " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }
}