import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// nel client ci vuole solo il socket, perche non c'è l'ascolto, o meglio non c'e l'ascolto bloccante da cui tutto inizia(accept).
public class MyClient {
    Socket socket; //non ho bisogno del SErver socket, pero ho bisogno dell'indirizzo e porta
    private String address;//non metto il getter e setter, solo io nel costruttore li posso impostare , gli altri si possono solo attaccare
    private int port;

    public static void main(String args[]) {
        if (args.length!=2){
            System.out.println("usage: java MyClient <address> <porta>");
            return;
        }
        
        MyClient client = new MyClient(args[0], Integer.parseInt(args[1]));//sto creando l'oggetto che vuole indirizzo  e porta
        client.start();
    }

    public MyClient(String address, int port) {//ho usato il costruttore
        this.address = address;
        this.port = port;
    }

    public void start() {
        System.out.println("Starting client connection to " + address + ":" +port);
        try {
            //effettuo la connessione
            socket = new Socket(address, port);//costruisco l'oggetto socket
            System.out.println("Started client connection to "+address+":"+port);
            //il client vuole le stringhe e le ributta dall'altra parte
            //- abbiamo bisogno di parlare con un cliente (e abbiamo il socket dichiarato)
            //- dal punto di vista degli stream :abbiamo bisgno dello stream quando noi mandiamo il messaggio, e lui poi lo convertira (PrintWriter)
            //- lo scanner ci servià per ricevere(x leggere) i messaggi dal server
            //- ed uno scanner x leggere la stringa dall'utente per poi mandarla al server

            //al server
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            //dal server
            Scanner scanner=new Scanner(socket.getInputStream());
            //dall'user
            Scanner user_scanner= new Scanner((System.in));//prendo da tastiera e le mando al server, che a sua volta ce le rinvierà in maiuscolo

            boolean go=true;



            while(go){
                System.out.println("Inserisci una stringa da inviare:");
                String message_to_send= user_scanner.nextLine();
                System.out.println("Sto inviando "+message_to_send);
                pw.println(message_to_send);//l'ho mandato
                pw.flush();

                String received_message=scanner.nextLine();
                System.out.println("ho ricevuto: "+received_message);

                if(message_to_send.equals("QUIT")){
                    System.out.println(" client: Sto chiudendo la connessione a "+socket.getRemoteSocketAddress());
                    socket.close();
                    go=false;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}