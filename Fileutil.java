package bloodbank;

import java.io.*;
import java.util.ArrayList;

public class Fileutil {
    private static final String FILE_NAME = "bloodbank_data.dat";

    public static void saveData(ArrayList<Donor>    donors,
                                ArrayList<Receiver> receivers,
                                ArrayList<Donation> donations) {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(donors);
            oos.writeObject(receivers);
            oos.writeObject(donations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Object[] loadData() {
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ArrayList<Donor>    donors    = (ArrayList<Donor>)    ois.readObject();
            ArrayList<Receiver> receivers = (ArrayList<Receiver>) ois.readObject();
            ArrayList<Donation> donations = (ArrayList<Donation>) ois.readObject();
            return new Object[]{donors, receivers, donations};
        } catch (Exception e) {
            return null;
        }
    }
}
