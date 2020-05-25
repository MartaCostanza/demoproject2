import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class RMIClient {
    public static void main(String args[]){
        //c'e il menu ecc, in base alla scelta.. faccio cose..
        String address=args[0];
        String rmi_name=args[1];

        try {
            RMIServices server= (RMIServices) Naming.lookup("rmi://"+address+"/"+rmi_name);//il cliente cerca la connessione nel registro RMI

            System.out.println("L'ora attuale è: ");
            System.out.println(server.getDate());//è eseguito dal server

            //Scanner s =new Scanner(System.in);
            //String line=s.nextLine();

            System.out.println("Converted to up:"+server.toUp("ciao"));

            boolean go=true;
            Scanner user_input=new Scanner(System.in);
            while (go){
                System.out.println("_____________________________");
                System.out.println("0_add person");
                System.out.println("1_list");
                System.out.println("2_quit");
                System.out.println("3_big complex computation");
                System.out.println("______________________________");
                System.out.println("choice:");

                int choice=user_input.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Name:");
                        String name = user_input.next();
                        System.out.println("age:");
                        int age = user_input.nextInt();
                        Person x = new Person(name, age);
                        server.addperson(x);
                        break;
                    case 1:
                        ArrayList<Person> mylist=server.getList();
                        System.out.println("received list->");
                        System.out.println(mylist);
                        break;
                    case 2:
                        go=false;
                        System.out.println("QUITTING CLIENT");
                        break;
                    case 3:
                        server.doIntensiveTask();
                        break;
                }
            }

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
