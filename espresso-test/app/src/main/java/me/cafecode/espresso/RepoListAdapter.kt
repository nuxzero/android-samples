package me.cafecode.espresso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.cafecode.espresso.models.Repo


class RepoListAdapter(var repoList: List<Repo>?, val clickListener: (repo: Repo) -> Unit) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        rootView.setOnClickListener {}
        return RepoViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return repoList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoList = this.repoList ?: return
        val repo = repoList[position]
        holder.bind(repo, clickListener)
    }

    class RepoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repo: Repo, clickListener: (repo: Repo) -> Unit) {
            val repoNameText = view.findViewById<TextView>(R.id.repo_name)
            val ownerNameText = view.findViewById<TextView>(R.id.owner_name)
            repoNameText.text = repo.fullName
            ownerNameText.text = repo.owner!!.login
            view.setOnClickListener { clickListener(repo) }
        }
    }
}