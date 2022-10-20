package beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.quadrants.Quadrant;
import domain.quadrants.QuadrantsInfo;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.interceptor.InvocationContext;
import lombok.Getter;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("quadrantsBean")
@ApplicationScoped
public class QuadrantsBean implements Serializable {
    @Getter
    private List<Quadrant> quadrants;

    @PostConstruct
    private void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/config.json");
            quadrants = mapper.readValue(inputStream, QuadrantsInfo.class).getQuadrants();
        } catch (Exception e) {
            quadrants = new ArrayList<>();
        }
    }
}
