import java.io.*;
import java.util.Hashtable;

public class Data {
    private BayesianFilter bfDta;

    public Data(){
        bfDta = new BayesianFilter();
    }

    public void store(String usr, double spamProb, double spamThresh, int setSize) throws IOException {
        Hashtable<Integer, String> prb = new Hashtable<Integer, String>();
        prb.put(42, "vida");
        prb.put(24, "muerte");
        prb.put(666, "satan");

        String path = System.getProperty("user.home") + "//Documents/BayesianFilter//Users" + "//" + usr + "/";
        File uData = new File(path + "/hashData.txt"); //Usaer data, hacemos un archivo
        FileWriter hashData = new FileWriter(uData, true); // no se que para poder escribir
        hashData.write("HashTable: " + prb.toString());// escribimos :v
        hashData.append(" -SpamProb: " + spamProb + " -SpamThresh: " + spamThresh + " -SetSize" + setSize);
        hashData.close();
    }

    public void load(String usr) throws FileNotFoundException { //revisar porque no es tipo void

        String path = System.getProperty("user.home") + "//Documents/BayesianFilter//Users" + "//" + usr + "/";
        //File rData = new File(path + "/hashData.txt");
        FileReader read = new FileReader(path + "/hashData.txt");
        BufferedReader content = new BufferedReader(read);
    }

    public void delete(){

    }
}
