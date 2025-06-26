package sistemaserie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieResult {
    private Serie show;

    public Serie getShow() {
        return show;
    }

    public void setShow(Serie show) {
        this.show = show;
    }
}
