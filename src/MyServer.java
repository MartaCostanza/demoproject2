import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {
    ServerSocket socket;
    Socket client_socket;//è il socket x gestire i clienti
    private int port;

    public static void main(String args[]) { // punto di partenza quando scrivo da riga di comando java MyServer
        if (args.length != 1) {
            System.out.println("Uso di java Myserver <port>");
            return;
        }
        MyServer server = new MyServer(Integer.parseInt(args[0]));// uso il costruttore quindi come parametro devo dare la porta,la porta è il primo argomento che noi passiamo a java quando lo lanciamo
        server.start();
    }


    public MyServer(int port){ // costruttore di Myserver(stesso nome della classe con parametro int porta(mi sto prendendo la responsabilità di gestire i costruttori), sappiamo che un myServer(l'oggetto) non ha senso se non gli passi una porta da cui partire
        System.out.println("Sto inizializzando il server con la porta "+port);
        this.port=port;//questa porta locale(l'ho dichiarata come privata prima) è proprio la porta che gli passo, da questo punto in poi una volta assegnata non si puo modificare .
        //sto dicendo che non posso costruire un oggeto se la private port non è impostata
    }

public void start(){ // prima l'ho inizializzato ,questo è il punto in cui parto
        try{
            System.out.println("Sto inizializzando il server sulla porta "+port);
            socket=new ServerSocket(port);
            System.out.println("Ho inizializzato il server sulla porta "+port);
            while(true){// metto il while perche appena "sbrigo" il primo cliente, chiudo il socket e ritorno in attesa su un'altro accept ciè in ascolto di un'altro cliente

                System.out.println("In ascolto sulla porta "+port);
                client_socket =socket.accept();//quando si sblocca dall'accept avremo il client su cui discutere
                System.out.println("Ho accettato la connessione da "+ client_socket.getRemoteSocketAddress());// mi dice l'indirizzo da cui veniva la chiamata

                //adesso do il servizio: ogni cliente che arriva noi rispondiamo alle sue richieste offrendo un servizio,(dipende da quello che ci ha detto) questa volta è bidirezionale.
                //servizio: in tale caso appena mi dici qualcosa, io te la rimando uguale in maiuscolo( è un esempio del fatto che il mio servizio dipende da quello che mi chiedi.

                //come leggo dal client che mi butta messaggi? Ho bisogno di un InputStream che viene dato dal client_socket
                Scanner client_scanner=new Scanner(client_socket.getInputStream()); // ho attaccato il mio socket in lettutra alla connessione che mi è appena arrivata(dal client).
                PrintWriter pw =new PrintWriter(client_socket.getOutputStream());// devo leggere e scrvere ambo i lati. IL serVer che abbiamo creato ha questo scanner per leggere, ma deve anche " buttargli" l'output (trasformato in maiuscolo).HO bisogno quindi del PrintWriter

                //Ora che posso leggere e scivere, inizia la comunicazione, Fino a quando può continuare?
                //DA QUI INIZIO IL MIO PROTOCOLLO( qual è il modo in cui possono parlare)
                // qui il mio protocollo consiste nel chiedermi una cosa e ti rispondo fino a quando non mi dici QUIT( in tal caso ti rispondo quit e ti saluto)

                // il mio ciclo dura x sempre finoa quando la booleana è falsa
                boolean go=true;
                while(go){
                    String message=client_scanner.nextLine();//la string message è quello che ricevo, prendo la prossima linea terminando dall'invio
                    System.out.println("Server: Ho ricevuto "+message);

                    String message_big=message.toUpperCase();//una volta ricevuto il messaggio la converto in maiuscolo
                    System.out.println("Server: Sto inviando "+ message_big+ "a"+client_socket.getRemoteSocketAddress());
                    pw.println(message_big);//il server sto stampando il mess maiuscolo
                    pw.flush();

                    if(message_big.equals("QUIT")){
                        System.out.println("Server: Sto chiudendo la connessione a "+client_socket.getRemoteSocketAddress() );
                        client_socket.close();
                        go=false;// ritono al while esterno dove si mette di nuovo in accept

                    }
                }
            }

        }catch(IOException e){
            System.out.println("Non posso inizializzare il server sulla porta "+port);
            e.printStackTrace();
        }

}

}
