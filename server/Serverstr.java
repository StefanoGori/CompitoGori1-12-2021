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
    Vector <String> a= new Vector<String>();


    public Socket attendi(){
        try{
            System.out.println("SERVER partito in esecuzione...");
            server =new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient= new DataOutputStream(client.getOutputStream());
            outVersoClient.writeBytes("Connessione riuscita"+'\n'); //messaggio inviato per verificare la connessione

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
        }
        return client;
    }

    public void comunica(){
        try{
            for(;;){
            outVersoClient.writeBytes("inserisci la nota da visualizzare o digita LISTA per vedere gli oggetti inseriti:"+'\n');
            //rimango in attesa della riga trasmessa dal client
            stringaRicevuta= inDalClient.readLine();
            if(stringaRicevuta.equals("LISTA")){
                outVersoClient.writeBytes("la lista delle note è: "+'\n');
                for(int i=0;i<a.size();i++){
                    outVersoClient.writeBytes(a.get(i)+'\n');
                }
                outVersoClient.writeBytes("Fine"+'\n');

            }
            else{
                a.add(stringaRicevuta);
                System.out.println("stringa dal cliente: "+stringaRicevuta);
                outVersoClient.writeBytes("Nota salvata!"+'\n');
            }
            
            


            // //la modifico e la rispedisco al client
            // stringaModificata=stringaRicevuta.toUpperCase();
            // System.out.println("7 invio la stringa modificata al client...");
            // outVersoClient.writeBytes(stringaModificata+'\n');

            //termina elaborazione sul server: chiudo la connessione del client

            // System.out.println("9 SERVER: fine elaborazione... buona notte!");
            // client.close();
            }

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