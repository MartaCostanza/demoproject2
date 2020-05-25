import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServices extends Remote { //saranno i metodi che offrirà il Server
    public String getDate() throws RemoteException;//getDate non vuole niente ma ritorna una stringa
    public String toUp(String s) throws RemoteException;//toUp vuole una stringa e ritorna una stringa
    public ArrayList<Person> getList() throws RemoteException;
    public  void addperson(Person p) throws RemoteException;
    public void doIntensiveTask() throws RemoteException;

}
