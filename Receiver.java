package bloodbank;

import java.io.Serializable;

public class Receiver extends Person implements Serializable {
    private String bloodGroup;

    public Receiver(String name, int age, String contact, String bloodGroup) {
        super(name, age, contact);
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroup()        { return bloodGroup; }
    public void   setbloodgroup(String b){ bloodGroup = b; }

    @Override
    public String toString() {
        return getName() + " | Age: " + age + " | Contact: " + contact + " | Blood Group: " + bloodGroup;
    }
}
