package org.aplicacao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowWrapper {
    @JsonProperty("show")
    private ProcuraFilmeDto show;

    public ProcuraFilmeDto getShow() {
        return show;
    }

    public void setShow(ProcuraFilmeDto show) {
        this.show = show;
    }
}
