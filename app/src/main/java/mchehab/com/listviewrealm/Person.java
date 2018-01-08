package mchehab.com.listviewrealm;

import org.parceler.Parcel;

import io.realm.PersonRealmProxy;
import io.realm.RealmObject;

/**
 * Created by muhammadchehab on 1/6/18.
 */
@Parcel(implementations = { PersonRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Person.class })
public class Person extends RealmObject{

    protected String firstName;
    protected String lastName;
    protected int age;

    public Person() {
        firstName = lastName = "";
        age = 0;
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void copyPerson(Person person){
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setAge(person.getAge());
    }

}
