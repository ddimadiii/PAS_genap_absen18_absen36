package com.example.pas_genap_absen18_absen36.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pas_genap_absen18_absen36.R;
import com.example.pas_genap_absen18_absen36.adapters.TeamAdapter;
import com.example.pas_genap_absen18_absen36.models.Team;
import com.example.pas_genap_absen18_absen36.models.TeamResponse;
import com.example.pas_genap_absen18_absen36.networks.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpainFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TeamAdapter teamAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_spain, container, false);

        recyclerView = root.findViewById(R.id.rvSpain);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = root.findViewById(R.id.progressBar);

        loadTeamData();

        return root;
    }

    private void loadTeamData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<TeamResponse> call = apiService.getSpainLeagueTeams("Soccer", "Spain");

        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(@NonNull Call<TeamResponse> call, @NonNull Response<TeamResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Team> teamList = response.body().getTeams();
                    if (teamList != null) {
                        teamAdapter = new TeamAdapter(getContext(), teamList);
                        recyclerView.setAdapter(teamAdapter);
                    } else {
                        Toast.makeText(getContext(), "Data team kosong!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TeamResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
