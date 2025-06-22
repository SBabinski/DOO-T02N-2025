package com.doo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.doo.view.WeatherAppView;

public class Main {
       public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            WeatherAppView app = new WeatherAppView();
            app.setVisible(true);
        });
    }
}