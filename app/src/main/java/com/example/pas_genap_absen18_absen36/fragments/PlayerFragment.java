package com.example.pas_genap_absen18_absen36.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pas_genap_absen18_absen36.R;
import com.example.pas_genap_absen18_absen36.adapters.PlayerAdapter;
import com.example.pas_genap_absen18_absen36.models.Player;
import com.example.pas_genap_absen18_absen36.models.PlayerResponse;
import com.example.pas_genap_absen18_absen36.networks.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PlayerAdapter playerAdapter;
    private List<Player> playerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player, container, false);

        recyclerView = root.findViewById(R.id.rvPlayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerAdapter = new PlayerAdapter(requireContext(), playerList);
        recyclerView.setAdapter(playerAdapter);
        progressBar = root.findViewById(R.id.progressBar);

        loadPlayers();

        return root;
    }

    private void loadPlayers() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        String[] playerNames = {"Danny_Welbeck", "Harry_Kane", "Marcus_Rashford", "Bukayo_Saka", "Raheem_Sterling"};

        for (String name : playerNames) {
            Call<PlayerResponse> call = apiService.searchPlayers(name);
            call.enqueue(new Callback<PlayerResponse>() {
                @Override
                public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null && response.body().getPlayer() != null) {
                        playerList.add(response.body().getPlayer().get(0)); // hanya ambil 1 pemain per nama
                        playerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PlayerResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Gagal memuat pemain", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
