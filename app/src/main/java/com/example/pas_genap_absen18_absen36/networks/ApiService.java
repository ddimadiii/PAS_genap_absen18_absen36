package com.example.pas_genap_absen18_absen36.networks;
import com.example.pas_genap_absen18_absen36.models.PlayerResponse;
import com.example.pas_genap_absen18_absen36.models.TeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {




    @GET("search_all_teams.php")
    Call<TeamResponse> getSpainLeagueTeams(
            @Query("s") String sport,
            @Query("c") String country
    );
    @GET("searchplayers.php")
    Call<PlayerResponse> searchPlayers(@Query("p") String playerName);


}
