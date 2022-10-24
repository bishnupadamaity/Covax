package com.covax.Services;

import com.covax.Model.State;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {
    URI uri = URI.create("https://cdn-api.co-vin.in/api/v2/admin/location/states");

    private List<State> allStates = new ArrayList<>();
    public List<State> getAllStates(){
        return allStates;
    }

    @PostConstruct
    public void fetchStateData() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(uri)
                .header("Accept","application/json")
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(response.body());

        JSONObject obj = new JSONObject(response.body());

        List<String> list = new ArrayList<String>();
        JSONArray array = obj.getJSONArray("states");
        for(int i = 0 ; i < array.length() ; i++){
            list.add(array.getJSONObject(i).getString("state_name"));
        }

        System.out.println(Arrays.toString(list.toArray()));
        list.forEach(System.out::println);

    }
}
