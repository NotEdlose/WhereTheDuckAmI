/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather {
  
    private String apiKey;
    private String city;
    private int temperature;
    private String weatherWidgetUrl;

    public Weather(String apiKey, String city) {
        this.apiKey = apiKey;
        this.city = city;
        this.getWeatherData();
    }

    private void getWeatherData() {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?id=" + this.city + "&appid=" + this.apiKey + "&units=metric";
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                json.append(tmp).append("\n");
            }
            reader.close();
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(json.toString());
            JSONObject main = (JSONObject) data.get("main");
            double temp = ((Number) main.get("temp")).doubleValue();
            this.temperature = (int) Math.round(temp);
            JSONArray weather = (JSONArray) data.get("weather");
            JSONObject weatherObj = (JSONObject) weather.get(0);
            String iconCode = (String) weatherObj.get("icon");
            this.weatherWidgetUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
        } catch (IOException e) {
            System.out.println("Error while getting weather data: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Error while parsing weather data: " + e.getMessage());
        }
    }

    public int getTemperature() {
        return this.temperature;
    }

    public String getWeatherWidgetUrl() {
        return this.weatherWidgetUrl;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public String getAPIKey(){
        return this.apiKey;
    }
    
    public static void main(String[] args){
        Weather weather = new Weather("ab4503d6f453b8fe5bcdba6e734b0b30","6058560");
        weather.getWeatherData();
        System.out.println(weather.getTemperature());
    }
}