package controller;

import service.SeriesService;

public class Main {
    public static void main(String[] args) {
        SeriesService seriesService = new SeriesService();
        seriesService.jsonInitialization();
    }
}
