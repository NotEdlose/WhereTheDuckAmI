/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 *
 * @author edwinor
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class WeatherApp extends JPanel {

    private Weather weather;

    private JLabel iconLabel;
    private JLabel tempLabel;
    private BufferedImage weatherImage;

    public WeatherApp() {
        // Initialize the weather object with your API key and city ID
        this.weather = new Weather("ab4503d6f453b8fe5bcdba6e734b0b30", "6058560");

        // Create the icon label and add it to the panel
        try {
            URL url = new URL(this.weather.getWeatherWidgetUrl());
            this.weatherImage = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Error while loading weather image: " + e.getMessage());
        }
        ImageIcon icon = new ImageIcon(this.weatherImage);
        this.iconLabel = new JLabel(icon);
        this.add(this.iconLabel);

        // Create the temperature label and add it to the panel
        this.tempLabel = new JLabel(this.weather.getTemperature() + "°C");
        this.add(this.tempLabel);

        // Set the panel layout and background color
        this.setLayout(new FlowLayout());
//        this.setBackground(Color.TRANSPARENT);
        this.setBounds(800,800,20,20);
    }

    public static void main(String[] args) {
        // Create a new JFrame and add the WeatherPanel to it
        JFrame frame = new JFrame("Weather Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new WeatherApp());
        frame.pack();
        frame.setVisible(true);
    }
}