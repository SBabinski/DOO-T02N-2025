package models;

public class Serie {
    public Show show;

    public void showInfo() {
        System.out.println("ID: " + show.id + " | Nome: " + show.name + " | Genero: " + show.genres);
        if (show.rating != null && show.rating.average != 0.0) {
            System.out.println("Nota média: " + show.rating.average);
        } else {
            System.out.println("Nota média: Indisponível");
        }
    }

    public void detailInfo() {
        showInfo();
        System.out.println("Estado: " + show.status + " | Estreia: " + show.premiered + " | Término " + show.ended);
        if (show.network != null) {
            System.out.println("Línguagem: " + show.language + " | Emissora: " + this.show.network.name);
        } else {
            System.out.println("Línguagem: " + show.language + " | Emissora: " + this.show.webChannel.name);
        }
        System.out.println("Sumario:\n" + show.summary);
    }




}
