package com.example.joguim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<Team> teams = new ArrayList<>();
    private Context context;
    private OnTeamLongClickListener onTeamLongClickListener;

    public interface OnTeamLongClickListener {
        void onTeamLongClick(Team team, int position);
    }

    public TeamAdapter(Context context) {
        this.context = context;
    }

    public void setOnTeamLongClickListener(OnTeamLongClickListener listener) {
        this.onTeamLongClickListener = listener;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.textViewTeamNumber.setText("Time " + team.getTeamNumber());
        
        // Criar lista de jogadores
        StringBuilder players = new StringBuilder();
        for (Player player : team.getPlayers()) {
            if (players.length() > 0) {
                players.append("\n");
            }
            players.append("• ").append(player.getName())
                  .append(" (").append(player.getPosition().getDisplayName()).append(")");
        }
        holder.textViewPlayers.setText(players.toString());

        // Configurar clique longo
        holder.itemView.setOnLongClickListener(v -> {
            if (onTeamLongClickListener != null) {
                onTeamLongClickListener.onTeamLongClick(team, position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    public void addTeam(Team team) {
        teams.add(team);
        notifyItemInserted(teams.size() - 1);
    }

    public void removeTeam(int position) {
        if (position >= 0 && position < teams.size()) {
            teams.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, teams.size());
        }
    }

    public void clearTeams() {
        teams.clear();
        notifyDataSetChanged();
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTeamNumber;
        TextView textViewPlayers;

        TeamViewHolder(View itemView) {
            super(itemView);
            textViewTeamNumber = itemView.findViewById(R.id.textViewTeamNumber);
            textViewPlayers = itemView.findViewById(R.id.textViewPlayers);
        }
    }
} 