import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

public class RMIServer extends UnicastRemoteObject implements RMIServices {
    Person_list person_list=new Person_list();
    protected RMIServer() throws RemoteException {//costruttore in cui dichiaro di avere questa eccezione
        super(7500);
    }

    public void Othersturff(){
        //fa alatre cose, ma non fa parte dell'interfaccia e non sara visi esternamente
    }

    //implemento i servizi
    @Override
    public String getDate() throws RemoteException{
        System.out.println("SERVER LOG: invoked getDate()");
        return new Date().toString();
    }

    @Override
    public String toUp(String s) throws RemoteException {
        System.out.println("SERVER LOG: Invoked TOUP()");
        return s.toUpperCase();
    }

    @Override
    public ArrayList<Person> getList() throws RemoteException {
        System.out.println("LOG SERVER: Invoking getList()");
        return person_list.getList();
    }

    @Override
    public void addperson(Person p) throws RemoteException {
        System.out.println("SERVERLOG: Sto invocando addperson()");
        person_list.addperson(p);

    }

    @Override
    public synchronized void doIntensiveTask() throws RemoteException {
        System.out.println("Thread tha invoked doIntensive ask:"+Thread.currentThread().getName());//rmi crea thread diversi
        System.out.println("sto facendo qualcosa..");
        int i=0;
        while(i>0){
            System.out.println("completo al " +i+"%");
            i=i+10;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("completo!");
    }


    public static void main(String arg[]){
        try {
            RMIServices services =new RMIServer();//ho assegnato il server all'interfaccia, perche di server voglio mostrare solo i servizi
            //per i test locali
           // Naming.rebind("listserver",services);//nome a cui si registra il server, tramite metodo statico rebind
            System.setProperty("java.rmi.server.hostname","192.168.1.2");//invece dell'indirizzo mettere whitelodge.ns0.it
            Registry registry= LocateRegistry.getRegistry();
            registry.rebind("rmiservices",services);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
