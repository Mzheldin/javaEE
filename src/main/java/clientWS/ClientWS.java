package clientWS;

import services.ws.ProductServiceWS;

import java.net.MalformedURLException;
import java.net.URL;

public class ClientWS {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/firsrwildflyjee/ProductServiceWS/ProductBeanWS?WSDL");


    }
}
