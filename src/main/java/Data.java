import java.io.*;
import java.util.Hashtable;

/**
 * Clase que se encarga de almacenar todos los datos del usuario
 */
public class Data {
    /**
     * Constructor de la clase
     */
    public Data(){
    }

    public void store(String usr, double spamProb, double spamThresh, int setSize, Hashtable<String, Float> tabla) throws IOException {

        String path = System.getProperty("user.home") + "//Documents/BayesianFilter//Users" + "//" + usr + "/";
        File uData = new File(path + "/hashData.txt"); //Usaer data, hacemos un archivo
        FileWriter hashData = new FileWriter(uData, true); // no se que para poder escribir
        hashData.write("HashTable: " + tabla.toString() + '\n');// escribimos
        hashData.append("SpamProb: " + spamProb + '\n'+ "SpamThresh: " + spamThresh + '\n' + "SetSize: " + setSize);
        hashData.close();
    }

    public void load(String usr) throws IOException {

        String path = System.getProperty("user.home") + "//Documents/BayesianFilter//Users" + "//" + usr + "/";
        //File rData = new File(path + "/hashData.txt");
        FileReader read = new FileReader(path + "/hashData.txt");
        BufferedReader content = new BufferedReader(read);
        try {
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String usr){
        String path = System.getProperty("user.home") + "//Documents/BayesianFilter/Users" + "/" + usr + "//hashData.txt";
        File toDelete = new File(path);
        toDelete.delete();
    }
}
