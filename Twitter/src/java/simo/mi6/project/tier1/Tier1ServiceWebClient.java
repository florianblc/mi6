package simo.mi6.project.tier1;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class Tier1ServiceWebClient {

    private static WebResource serviceWebTier2 = null;

    private static String[] getAllUsers() throws Exception {

        String users = serviceWebTier2.path("getAllUsers").get(String.class);

        return users.substring(0, users.length() - 1).substring(1).split(", ");
    }

    public static void main(String args[]) throws Exception {

        serviceWebTier2 = Client.create().resource("http://localhost:8080/Twitter");

        for (String user : getAllUsers()) {
            System.out.println(user);
        }

    }
}
