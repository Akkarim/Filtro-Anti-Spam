import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import javax.mail.MessagingException;

/**
 * Clase que se encarga del algoritmo
 */
public class BayesianFilter {

    private  GmailRetriever mail;
    private Email email;
    private double spamProbab;
    private double spamThreshold;
    private int sizeOfTrainSet;
    private Word word;
    private double wordProb;
    private Hashtable<String,Float> dictionary;
    private int length;

    /**
     * Constructor de la clase
     */
    public BayesianFilter(){
        dictionary = new Hashtable<String,Float>();
        mail = new GmailRetriever();
        spamProbab = 0.3;
        spamThreshold = 0.9;
        sizeOfTrainSet = 0;
        word = new Word();
        email = new Email();
        length = 0;
    }

    /**
     * Define la proba de spam
     * Requiere que este entre 0 y 1
     * @param spamProbab Nueva proba de spam
     */
    public void setSpamProbab(double spamProbab) {

        this.spamProbab = spamProbab;
    }

    public double getSpamProbab(){
        return spamProbab;
    }

    /**
     * Límite para que un correo sea considerado spam
     * @param spamThreshold límite
     */
    public void setSpamThreshold(double spamThreshold) {

        this.spamThreshold = spamThreshold;
    }

    public double getSpamThreshold() {
        return spamThreshold;
    }

    public int getSizeOfTrainSet() {
        return sizeOfTrainSet;
    }

    /**
     * Agrega al Hash una palabra con su respectiva proba
     * @param s Palabra
     * @param prob Proba asociada
     */
    public void addToDictionary(String s, float prob){

        dictionary.put(s,prob);
    }

    /**
     * Número de correos para el entrenamiento
     * @param newSize Nuevo tamaño
     */
    public void setSizeOfTrainSet(int newSize){

        sizeOfTrainSet = newSize;
    }

    /**
     * Lista con los correos de spam
     * @return Lista de mensajes spam
     * @throws IOException Excepción de java
     */
    public List<Message> spamSet() throws IOException {
        List<Message> spamMessages = new ArrayList<Message>();
        spamMessages = mail.getMessages("in:Spam");
        return spamMessages;
    }

    /**
     * Lista con los correos en in:Inbox
     * @return Lista de mensajes nospam
     * @throws IOException Excepción de java
     */
    public List<Message> noSpamSet() throws IOException {
        List<Message> notSpamMessages = new ArrayList<Message>();
        notSpamMessages = mail.getMessages("in:Inbox");
        return notSpamMessages;
    }

    /**
     * Saca la proba de cada palabra
     * @param query Consulta spam o inbox
     * @return Hash Table
     * @throws IOException Excepción de java
     * @throws MessagingException Excepción de java
     */
    public Hashtable<String, Integer> setProbForEmail(String query) throws IOException, MessagingException {
        Hashtable<String, Integer> freq = new Hashtable<String, Integer>();
        int counter = 0;
        int length = 0;
        String msgs = "";
        if (query.equals("in:Spam")){
            length = spamSet().size();
        } else{
            length = noSpamSet().size();
        }
        for (int i= 1; i < 50; i++) {
            msgs = mail.getBody(query, i);
            msgs = msgs.replace('.', ' ');msgs = msgs.replace('•', ' ');
            msgs = msgs.replace('?', ' ');msgs = msgs.replace('¿', ' ');
            msgs = msgs.replace('¡', ' ');msgs = msgs.replace('"', ' ');
            msgs = msgs.replace('@', ' ');msgs = msgs.replace('©', ' ');
            msgs = msgs.replace('#', ' ');msgs = msgs.replace(':', ' ');
            msgs = msgs.replace(';', ' ');msgs = msgs.replace('0', ' ');
            msgs = msgs.replace('1', ' ');msgs = msgs.replace('2', ' ');
            msgs = msgs.replace('3', ' ');msgs = msgs.replace('4', ' ');
            msgs = msgs.replace('5', ' ');msgs = msgs.replace('6', ' ');
            msgs = msgs.replace('7', ' ');msgs = msgs.replace('8', ' ');
            msgs = msgs.replace('9', ' ');msgs = msgs.replace('(', ' ');
            msgs = msgs.replace(')', ' ');msgs = msgs.replace('-', ' ');
            msgs = msgs.replace('_', ' ');msgs = msgs.replace('|', ' ');
            msgs = msgs.replace('/', ' ');msgs = msgs.replace('&', ' ');
            msgs = msgs.replace(',', ' ');msgs = msgs.replace('!', ' ');
            msgs = msgs.replace('=', ' ');msgs = msgs.replace('$', ' ');
            msgs = msgs.replace('\r', ' '); msgs = msgs.replace('\n', ' ');

            String[] setOfWords = msgs.split(" +");

            for (String s : setOfWords) {
                if (freq.containsKey(s)) {
                    freq.put(s, freq.get(s)+1);
                } else {
                    freq.put(s, 1);
                }
            }
        }
            return freq;
    }

    public Hashtable<String, Float> setWord(Hashtable<String, Integer> freq, String query) throws IOException {
        float pb = 0.0F;
        int tmp = 0;
        if (query.equals("in:Spam")){
            length = spamSet().size(); // Duran mucho
        } else{
            length = noSpamSet().size(); // Dura muchi
                    }
        Enumeration<String> e = freq.keys();
        String caca = e.nextElement();
        System.out.println(caca);
        while(e.hasMoreElements()){
            tmp = freq.get(e.nextElement());
            pb = (float) tmp / length;
            if(e.nextElement()!=null){
                dictionary.put(e.nextElement(), pb);
            }

        }
        return dictionary;
    }
}