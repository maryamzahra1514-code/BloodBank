package bloodbank;

import java.io.Serializable;

class Person implements Serializable {
    protected String name;
    protected int age;
    protected String contact;

    public Person(String name, int age, String contact) {
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public String getName()    { return name; }
    public int    getAge()     { return age; }
    public String getContact() { return contact; }

    public void setname(String n)   { name = n; }
    public void setage(int a)       { age = a; }
    public void setcontact(String c){ contact = c; }
}
