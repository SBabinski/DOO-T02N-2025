package org.aplicacao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcuraFilmeDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("language")
    private String language;
    @JsonProperty("genres")
    private List<String> genres;
    @JsonProperty("rating")
    private Rating rating;
    @JsonProperty("status")
    private String status;
    @JsonProperty("premiered")
    private String premiered;
    @JsonProperty("ended")
    private String ended;
    @JsonProperty("officialSite")
    private String officialSite;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("network")
    private Network network;
    @JsonProperty("image")
    private Image image;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getOfficialSite() {
        return officialSite;
    }

    public void setOfficialSite(String officialSite) {
        this.officialSite = officialSite;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Network {
        @JsonProperty("name")
        private String nome;
        @JsonProperty("country")
        private Country country;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "Network{" +
                    "nome='" + nome + '\'' +
                    ", country=" + country +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Country {
        @JsonProperty("name")
        private String nome;
        @JsonProperty("code")
        private String code;
        @JsonProperty("timezone")
        private String timezone;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        @Override
        public String toString() {
            return "Country{" +
                    "nome='" + nome + '\'' +
                    ", code='" + code + '\'' +
                    ", timezone='" + timezone + '\'' +
                    '}';
        }
    }

    public static class Image {
        @JsonProperty ("medium")
        private String medium;
        @JsonProperty ("original")
        private String original;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        @Override
        public String toString() {
            return "Image{" +
                    "medium='" + medium + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties (ignoreUnknown = true)
    public static class Rating {
        @JsonProperty ("average")
        private Double average;

        public Double getAverage() {
            return average;
        }

        public void setAverage(Double average) {
            this.average = average;
        }
    }


    @Override
    public String toString() {
        return "ProcuraFilmeDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", genres=" + genres +
                ", rating=" + rating +
                ", status='" + status + '\'' +
                ", premiered=" + premiered +
                ", ended=" + ended +
                ", officialSite='" + officialSite + '\'' +
                ", summary='" + summary + '\'' +
                ", network=" + network +
                ", image=" + image +
                '}';
    }
}
