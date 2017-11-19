import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;

/**
 * Clase controladora que administra todo el programa
 */
public class Controller {
    private GmailRetriever mails;
    private String bf;
    private BayesianFilter filter;

    /**
     * Constructor de la clase
     */
    public Controller(){

        mails = new GmailRetriever();
        filter = new BayesianFilter();
        bf = "";
    }

    /**
     * Configura las opciones
     * @param spamProb
     * @param spamThresh
     * @param setSize
     */
    public void configuration(double spamProb, double spamThresh, int setSize){
        filter.setSpamProbab(spamProb);
        filter.setSpamThreshold(spamThresh);
        filter.setSizeOfTrainSet(setSize);
    }

    /**
     * Ejecuta un entrenamiento para el programa
     */
    public void train(){
        int counter = 42;

    }

    /**
     * Muestra las palabras y configraciones del usuario
     * @return txt
     */
    public String showData(){
        String data = "";
        return data;
    }

    /**
     * Obtiene la cantidad de mensajes en el buzón escogido
     * @param num
     * @param query
     * @return Array
     * @throws IOException
     */
    public Message getMail(int num, String query) throws IOException {
        List<Message> array = new ArrayList<Message>();
        array = mails.getMessages(query);// puede ser in:Spam o in:Inbox
        if (array.get(num)  != null){
            System.out.println(array.get(num));
            return array.get(num);
        } else {
            return null;
        }
    }

    /**
     * Cierra sesión y borra las credendiales del usuario
     */
    public static void logOut(){
        String path = System.getProperty("user.home")+ "//.credentials/gmail-java-Bayesian_Filter//StoredCredential";
        File data = new File(path);
        data.delete();
        System.out.println("Your cretentials has been deleted.");
    }

    /**
     * Inicia sesión con un nombre de usuario
     * @param usr
     * @throws IOException
     */

    public void logIn(String usr) throws IOException {
        bf = System.getProperty("user.home") + "//Documents/BayesianFilter//Users" + "//" + usr; //
        File bayesFilt = new File(bf);
        bayesFilt.mkdirs();
    }

}
