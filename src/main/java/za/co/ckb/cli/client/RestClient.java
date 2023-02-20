package za.co.ckb.cli.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.SneakyThrows;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RestClient {

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private String httpAccessKey;

    private Client client;
    RestClient(String httpAccessKeyParam){
        this.httpAccessKey = httpAccessKeyParam;

        Logger logger = Logger.getLogger(getClass().getName());

        final JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider().
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        client =  ClientBuilder.newClient(new ClientConfig(jacksonJsonProvider).
                register(new LoggingFeature(logger, Level.INFO, null, null))//.
        );
    }

    @SneakyThrows
    <T> T get(String uri, Class<T> returnType) {
        return client
                .target(uri)
                .request(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, "Bearer "+httpAccessKey)
                .get(returnType);
    }

    @SneakyThrows
    <T> T get(String uri, String pathValue, Class<T> returnType) {
        Response response;

        try {
            response = client
                    .target(uri)
                    .path(pathValue)
                    .request(MediaType.APPLICATION_JSON)
                    .header(AUTHORIZATION_HEADER_KEY, "Bearer " + httpAccessKey).get();

            if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
                String phrase = response.getStatusInfo().getReasonPhrase();
                System.err.println(phrase);
                throw new Exception(phrase);
            }
        } catch (Throwable t){
            System.err.println(t.getMessage());
            throw new Exception(t.getMessage());
        }

        return response.readEntity(returnType);
    }

    <T> Response post(String uri, T obj) {
        return client
                .target(uri)
                .request(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, "Bearer "+httpAccessKey)
                .post(Entity.entity(obj, MediaType.APPLICATION_JSON));
    }

}