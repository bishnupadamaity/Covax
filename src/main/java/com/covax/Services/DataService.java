package com.covax.Services;

import com.covax.Model.District;
import com.covax.Model.State;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;

import java.util.List;

@Service
public class DataService {
//    URI s_uri = URI.create("https://cdn-api.co-vin.in/api/v2/admin/location/states");

    private List<State> allStates = new ArrayList<>();
    private List<District> allDistricts = new ArrayList<>();


    public List<District> getAllDistricts() {
        return allDistricts;
    }

    public List<State> getAllStates(){
        return allStates;
    }



    @PostConstruct
    public void fetchStateData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create("https://cdn-api.co-vin.in/api/v2/admin/location/states"))
                .header("Accept","application/json")
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        JSONObject obj = new JSONObject(response.body());
        JSONArray array = obj.getJSONArray("states");
        for(int i = 0 ; i < array.length() ; i++){
            State state = new State();
            state.setId(String.valueOf(array.getJSONObject(i).getInt("state_id")));
            state.setName(array.getJSONObject(i).getString("state_name"));
            allStates.add(state);
        }
    }
    @PostConstruct
    public void fetchDistrictData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create("https://cdn-api.co-vin.in/api/v2/admin/location/districts/26"))
                .header("Accept","application/json")
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        JSONObject obj = new JSONObject(response.body());
        JSONArray array = obj.getJSONArray("districts");
        for(int i = 0 ; i < array.length() ; i++){
            District district = new District();
            district.setId(String.valueOf(array.getJSONObject(i).getInt("district_id")));
            district.setName(array.getJSONObject(i).getString("district_name"));
            allDistricts.add(district);
        }
    }
}
