package zfani.assaf.player_maker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_version_team.view.*
import zfani.assaf.player_maker.R
import zfani.assaf.player_maker.data.model.VersionTeam
import zfani.assaf.player_maker.ui.main.view.MainFragment

class VersionTeamAdapter : ListAdapter<VersionTeam, VersionTeamAdapter.VersionTeamViewHolder>(object :
    DiffUtil.ItemCallback<VersionTeam>() {
    override fun areItemsTheSame(oldItem: VersionTeam, newItem: VersionTeam): Boolean {
        return oldItem.teamId == newItem.teamId
    }

    override fun areContentsTheSame(oldItem: VersionTeam, newItem: VersionTeam): Boolean {
        return oldItem.majorVersion == newItem.majorVersion && oldItem.version == newItem.version
    }
}) {

    var onItemClickedListener: MainFragment.OnItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionTeamViewHolder {
        return VersionTeamViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_version_team, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VersionTeamViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VersionTeamViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(versionTeam: VersionTeam) {
            containerView.tvTeamId.text = versionTeam.teamId
            containerView.setOnClickListener {
                onItemClickedListener?.onItemClicked(versionTeam)
            }
        }
    }
}