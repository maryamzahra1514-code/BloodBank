package bloodbank;

import java.util.ArrayList;

public class Manager {
    private ArrayList<Donor>    donors;
    private ArrayList<Receiver> receivers;
    private ArrayList<Donation> donations;

    @SuppressWarnings("unchecked")
    public Manager() {
        Object[] data = Fileutil.loadData();
        if (data != null) {
            donors    = (ArrayList<Donor>)    data[0];
            receivers = (ArrayList<Receiver>) data[1];
            donations = (ArrayList<Donation>) data[2];
        } else {
            donors    = new ArrayList<>();
            receivers = new ArrayList<>();
            donations = new ArrayList<>();
        }
    }

    public void donateBlood(Donor donor, Receiver receiver) {
        if (donor.getBloodGroup().equalsIgnoreCase(receiver.getBloodGroup())) {
            donations.add(new Donation(donor, receiver, donor.getBloodGroup()));
            donors.remove(donor);
            receivers.remove(receiver);
            Fileutil.saveData(donors, receivers, donations);
        }
    }

    public ArrayList<Donation> getAllDonations() { return donations; }

    // --- Donor ---
    public void addDonor(String name, int age, String contact, String bloodGroup) {
        donors.add(new Donor(name, age, contact, bloodGroup));
        Fileutil.saveData(donors, receivers, donations);
    }

    public ArrayList<Donor> getAllDonors() { return donors; }

    public void removeDonor(Donor d) {
        donors.remove(d);
        Fileutil.saveData(donors, receivers, donations);
    }

    public ArrayList<Donor> searchDonorByBloodGroup(String bloodGroup) {
        ArrayList<Donor> result = new ArrayList<>();
        for (Donor d : donors)
            if (d.getBloodGroup().equalsIgnoreCase(bloodGroup))
                result.add(d);
        return result;
    }

    // --- Receiver ---
    public void addReceiver(String name, int age, String contact, String bloodGroupNeeded) {
        receivers.add(new Receiver(name, age, contact, bloodGroupNeeded));
        Fileutil.saveData(donors, receivers, donations);
    }

    public ArrayList<Receiver> getAllReceivers() { return receivers; }

    public void removeReceiver(Receiver r) {
        receivers.remove(r);
        Fileutil.saveData(donors, receivers, donations);
    }

    public ArrayList<Receiver> searchReceiverByBloodGroup(String blood) {
        ArrayList<Receiver> result = new ArrayList<>();
        for (Receiver r : receivers)
            if (r.getBloodGroup().equalsIgnoreCase(blood))
                result.add(r);
        return result;
    }
}
