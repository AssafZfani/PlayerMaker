package zfani.assaf.player_maker.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import zfani.assaf.player_maker.R
import zfani.assaf.player_maker.data.model.VersionTeam
import zfani.assaf.player_maker.ui.adapter.VersionTeamAdapter
import zfani.assaf.player_maker.ui.main.viewmodel.MainViewModel
import zfani.assaf.player_maker.ui.version_team.view.VersionTeamFragment
import zfani.assaf.player_maker.utils.Status


class MainFragment : Fragment() {

    companion object {
        private const val ANIMATION_DURATION = 1500L
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        rvVersionTeamList.run {
            itemAnimator?.addDuration = ANIMATION_DURATION
            adapter = VersionTeamAdapter().apply {
                onItemClickedListener = object : OnItemClickedListener {
                    override fun onItemClicked(versionTeam: VersionTeam) {
                        showVersionTeamFragment(versionTeam)
                    }
                }
            }
        }
    }

    private fun showVersionTeamFragment(versionTeam: VersionTeam) {
        val versionTeamFragment = VersionTeamFragment.newInstance(versionTeam)
        activity?.run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, versionTeamFragment, this@MainFragment.tag)
                .addToBackStack(this@MainFragment.tag).commit()
        }
    }

    private fun setupObservers() {
        viewModel.getVersionTeams().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    (rvVersionTeamList.adapter as VersionTeamAdapter).submitList(it.data)
                }
                Status.ERROR -> {
                }
            }
        })
    }

    interface OnItemClickedListener {
        fun onItemClicked(versionTeam: VersionTeam)
    }
}
