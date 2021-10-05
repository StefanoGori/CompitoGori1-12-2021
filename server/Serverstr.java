import java.io.*;
import java.net.*;
import java.util.*;

public class Serverstr{
    ServerSocket server =null;
    Socket client =null;
    String stringaRicevuta= null;
    String stringaModificata=null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;


    public Socket attendi(){
        try{
            System.out.println("1 SERVER partito in esecuzione...");
            server =new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient= new DataOutputStream(client.getOutputStream());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
        }
        return client;
    }

    public void comunica(){
        try{
            //rimango in attesa della riga trasmessa dal client
            System.out.println("3 Benvenuto client, scrivi una frase e la trasformo in maiuscolo. Attendo...");
            stringaRicevuta= inDalClient.readLine();
            System.out.println("6 ricevuta la stringa dal cliente: "+stringaRicevuta);

            //la modifico e la rispedisco al client
            stringaModificata=stringaRicevuta.toUpperCase();
            System.out.println("7 invio la stringa modificata al client...");
            outVersoClient.writeBytes(stringaModificata+'\n');

            //termina elaborazione sul server: chiudo la connessione del client
            System.out.println("9 SERVER: fine elaborazione... buona notte!");
            client.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col client!");
            System.exit(1);
        }
    }
    public static void main( String[] args )
    {
        Serverstr servente = new Serverstr();
        servente.attendi();
        servente.comunica();
    }

}