package tech.fastool.json.api.cfg;

/**
 * JSON Config
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Config {

    private boolean prettyFormat = false;

    private SerializationFeature[] serializationFeatures;

    public boolean isPrettyFormat() {
        return prettyFormat;
    }

    public void setPrettyFormat(boolean prettyFormat) {
        this.prettyFormat = prettyFormat;
    }

    public SerializationFeature[] getSerializationFeatures() {
        return serializationFeatures;
    }

    public void setSerializationFeatures(SerializationFeature[] serializationFeatures) {
        this.serializationFeatures = serializationFeatures;
    }

}
