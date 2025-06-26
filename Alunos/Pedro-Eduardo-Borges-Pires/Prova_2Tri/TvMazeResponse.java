package spring_boot_api.tvmaze_api;

import java.util.List;

public class TvMazeResponse {
   
	private Show show;

 
    public Show getShow() {
		return show;
	}
    
	public void setShow(Show show) {
		this.show = show;
	}

	public static class Show {
        
    	private String name;
        private String language;
        private List<String> genres;
        private Rating rating;
        private String status;
        private String premiered;
        private String ended;
        private Network network;

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

		public Rating getRating() {
			return rating;
		}

		public void setRating(Rating rating) {
			this.rating = rating;
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

		public Network getNetwork() {
			return network;
		}

		public void setNetwork(Network network) {
			this.network = network;
		}

		public static class Rating {
            private Double average;
            public Double getAverage() { 
            	return average != null ? average : 0.0; 
            	}
            
            public void setAverage(Double average) { 
            	
            	this.average = average;
            	
            }
        }

        public static class Network {
            
        	private String name;
            
            public String getName() { 
            	return name; }
           
            public void setName(String name) {
            	this.name = name; 
            	}
        }
    }
}
