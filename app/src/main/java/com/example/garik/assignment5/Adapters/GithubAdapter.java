package com.example.garik.assignment5.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garik.assignment5.Models.Repo;
import com.example.garik.assignment5.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GaRiK on 17.09.2018.
 */

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.ViewHolder> {


    private Context context;
    private List<Repo> repoList;

    public GithubAdapter(Context context, List<Repo> repoList) {
        this.context = context;
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repo repo = repoList.get(position);
        holder.repoName.setText(repo.getName());
        holder.repoFullname.setText(repo.getFullName());
        holder.repoWatches.setText(String.format("%d", repo.getWatchersCount()));
        holder.repoStars.setText(String.format("%d", repo.getStargazersCount()));
        holder.repoForks.setText(String.format("%d", repo.getForksCount()));


    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.repo_full_name)
        TextView repoFullname;
        @BindView(R.id.repo_watches)
        TextView repoWatches;
        @BindView(R.id.repo_stars)
        TextView repoStars;
        @BindView(R.id.repo_forks)
        TextView repoForks;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
