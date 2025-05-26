package com.example.pas_genap_absen18_absen36.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pas_genap_absen18_absen36.R;
import com.example.pas_genap_absen18_absen36.models.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private Context context;
    private List<Player> playerList;

    public PlayerAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.tvPlayerName.setText(player.getStrPlayer());
        holder.tvPlayerPosition.setText(player.getStrPosition());

        Glide.with(context)
                .load(player.getStrThumb())
                .into(holder.imgPlayer);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPlayer;
        TextView tvPlayerName, tvPlayerPosition;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlayer = itemView.findViewById(R.id.imgPlayer);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvPlayerPosition = itemView.findViewById(R.id.tvPlayerPosition);
        }
    }
}
