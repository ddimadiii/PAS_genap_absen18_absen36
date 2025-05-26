package com.example.pas_genap_absen18_absen36.networks;
import com.example.pas_genap_absen18_absen36.models.PlayerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {




    @GET("searchplayers.php")
    Call<PlayerResponse> searchPlayers(@Query("p") String playerName);


}
