package bsp.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "AUTH_REQUEST_MAP")
public class AuthRequestMap   {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "URL", length = 255, unique = true)
    private String url;
    @Column(name = "CONFIG_ATTRIBUTE", length = 255)
    private String configAttribute;

    public AuthRequestMap() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConfigAttribute() {
        return configAttribute;
    }

    public void setConfigAttribute(String configAttribute) {
        this.configAttribute = configAttribute;
    }
}
