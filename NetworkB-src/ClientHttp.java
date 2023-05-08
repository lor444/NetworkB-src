import java.io.*;
import java.net.Socket;

public class ClientHttp {

    private String host;
    private int port;

    public ClientHttp(String host, int port){
        this.host=host;
        this.port=port;
    }

    public String sendRequestAndRetriveResponse(String httpRequest){
        try{
            Socket socket = new Socket(host, port);
            sendHttpRequest(socket,httpRequest);
            String response = readHttpResponse(socket);
            socket.close();
            return response;
        }catch(Exception ex){
            return "Si e' verificato un problema nell'invio della richiesta: "+ex.toString();
        }
    }

    private void sendHttpRequest(Socket socket, String httpRequest) throws IOException {
        socket.getOutputStream().write(httpRequest.getBytes());
        socket.getOutputStream().flush();
    }

    private String readHttpResponse(Socket socket) throws IOException {
        StringBuilder response=new StringBuilder();
        String responseLine=null;
        boolean stopReading=false;
        InputStream in=socket.getInputStream();

        /*
        BufferedReader rdr=new BufferedReader(new InputStreamReader(in));
        int contentLength=0;
        while(!stopReading){
            responseLine=rdr.readLine();
            if(responseLine.toLowerCase().startsWith("content-length"))
                try{
                    // 01234567890123456789
                    // content-length: 1234
                    String cl=responseLine.substring(16);
                    contentLength=Integer.valueOf(cl);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            response.append(responseLine).append("\n");
            stopReading=responseLine==null;
            //stopReading=responseLine.compareTo("")==0;
        }
        if(contentLength>0){
            String body=read(in);
            response.append(body);
        }*/
        response.append(read(in));
        return response.toString();
    }

    private String read(InputStream in) throws IOException{
        StringBuilder sb=new StringBuilder();
        int bufferSize=128;
        byte[] buffer=new byte[bufferSize];
        int bytesRead=bufferSize;
        while(bytesRead==bufferSize){
            bytesRead=in.read(buffer);
            sb.append(new String(buffer,0,bytesRead));
        }
        return sb.toString();
    }

}
