
import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    public Socket connetti() {
        System.out.println("CLIENT partito in esecuzione...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new Socket(nomeServer, portaServer);
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
            stringRicevutaDalServer = inDalServer.readLine();
            System.out.println("S==> " + stringRicevutaDalServer);
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return miosocket;
    }

    public void comunica() {
        try {
            for (;;) {
                stringRicevutaDalServer = inDalServer.readLine();
                System.out.println("S==> " + stringRicevutaDalServer);
                stringaUtente = tastiera.readLine();
                System.out.println("invio la stringa al server e attendo...");
                outVersoServer.writeBytes(stringaUtente+'\n');
                stringRicevutaDalServer = inDalServer.readLine();
                if (stringRicevutaDalServer.equals("VITTORIA!")) {
                    break;
                }
                System.out.println("S==>" + stringRicevutaDalServer);
                stringRicevutaDalServer = inDalServer.readLine();
                System.out.println("S==>" + stringRicevutaDalServer);              
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        Client cliente = new Client();
        cliente.connetti();
        cliente.comunica();
    }

}
