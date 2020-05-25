import java.util.ArrayList;

public class Person_list {
    private ArrayList<Person> list=new ArrayList<>();

    public void addperson(Person p){
        list.add(p);
    }
    public ArrayList<Person> getList(){
        ArrayList<Person>anotherlist = new ArrayList<>();
        for(Person p:list){
            Person x=new Person(p.getName(),p.getAge());
            anotherlist.add(x);
        }
        return anotherlist;
    }


}
