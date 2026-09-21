package bloodbank;

import java.io.Serializable;

public class Donation implements Serializable {
    private Donor    donor;
    private Receiver receiver;
    private String   bloodGroup;

    public Donation(Donor donor, Receiver receiver, String bloodGroup) {
        this.donor      = donor;
        this.receiver   = receiver;
        this.bloodGroup = bloodGroup;
    }

    public void setDonor(Donor d)       { donor = d; }
    public void setreceiver(Receiver r) { receiver = r; }
    public void setbloodgroup(String b) { bloodGroup = b; }

    public Donor    getDonor()      { return donor; }
    public Receiver getReceiver()   { return receiver; }
    public String   getBloodGroup() { return bloodGroup; }

    @Override
    public String toString() {
        return "Donor: " + donor.toString()
             + " ---------→ Receiver: " + receiver.toString()
             + " | Blood donated: " + bloodGroup;
    }
}
