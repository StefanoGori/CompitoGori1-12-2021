import java.io.*;
import java.net.*;
import java.util.*;

public class Serverstr{
    ServerSocket server =null;
    Socket client =null;
    String stringaRicevuta= null;
    String stringaModificata=null;
    int stringaricevuta=0;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    Vector <Integer>a= new Vector<Integer>();
    int b=1;

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
                outVersoClient.writeBytes(" Dammi il numero estratto"+'\n');
                //rimango in attesa della riga trasmessa dal client
                stringaRicevuta= inDalClient.readLine();
                System.out.println(stringaRicevuta);
                //trasformo la stringa in int
                stringaricevuta=Integer.parseInt(stringaRicevuta);
                System.out.println("stringa trasformata in int");
                a.add(stringaricevuta);
                System.out.println(" size vettore: "+a.size());
                for(int i=0;i<a.size();i++){
                    if(stringaricevuta==a.get(i)){
                        outVersoClient.writeBytes("ERRORE: numero gia' presente"+'\n');
                        break;
                    }
                    else{
                        a.add(stringaricevuta);
                        Collections.sort(a);
                        System.out.println(" stringa dal cliente: "+stringaRicevuta);
                        outVersoClient.writeBytes(a+" I numeri estratti sono: "+'\n');
                        for(i=0;i<a.size();i++){
                            outVersoClient.writeBytes(a.get(i)+"-");
                        }
                        outVersoClient.writeBytes("\n");
                        for(i=0;i<a.size();i++){
                            if(a.get(i+1)-a.get(i)==1 && a.get(i+2)-a.get(i)==2 && a.get(i+3)-a.get(i)==3 && a.get(i+4)-a.get(i)==4){
                                outVersoClient.writeBytes("VITTORIA!"+'\n');
                                client.close();
                            }
                        }
                        break;
                    }
                }
                b++;
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