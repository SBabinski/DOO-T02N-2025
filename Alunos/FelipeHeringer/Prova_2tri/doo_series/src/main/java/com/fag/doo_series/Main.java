package com.fag.doo_series;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.fag.doo_series.view.SeriesAppGUI;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new SeriesAppGUI();
        });
    }
}