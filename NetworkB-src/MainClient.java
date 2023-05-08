import java.sql.SQLOutput;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {

        String host ="localhost";
        int port    =81;

        String request= """
GET / HTTP/1.1
Accept: */*
Host: localhost

        """;

        ClientHttp client=new ClientHttp(host,port);
        String esito=client.sendRequestAndRetriveResponse(request);
        System.out.println(esito);
    }

}
