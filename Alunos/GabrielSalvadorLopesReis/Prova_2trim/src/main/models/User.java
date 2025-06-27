package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String name;
    public List<Serie> seriesFavoritas = new ArrayList<>();
    public List<Serie> seriesAssistidas = new ArrayList<>();
    public List<Serie> seriesParaAssistir = new ArrayList<>();
}
