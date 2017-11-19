import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;

import javax.mail.MessagingException;

public class BayesianFilter {

    private  GmailRetriever mail;
    private Email email;
    private double spamProbab;
    private double spamThreshold;
    private int sizeOfTrainSet;
    private Word word;
    private double wordProb;
    private Hashtable<String,Float> dictionary;

    public BayesianFilter(){
        dictionary = new Hashtable<String,Float>();
        mail = new GmailRetriever();
        spamProbab = 0.3;
        spamThreshold = 0.9;
        sizeOfTrainSet = 0;
        word = new Word();
        email = new Email();
    }

    public void setSpamProbab(double spamProbab) {

        this.spamProbab = spamProbab;
    }


    public void setSpamThreshold(double spamThreshold) {

        this.spamThreshold = spamThreshold;
    }

    public void addToDictionary(String s, float prob){

        dictionary.put(s,prob);
    }

    public void setSizeOfTrainSet(int newSize){

        sizeOfTrainSet = newSize;
    }

    public List<Message> spamSet() throws IOException {
        List<Message> spamMessages = new ArrayList<Message>();
        spamMessages = mail.getMessages("in:Spam");
        return spamMessages;
    }

    public List<Message> noSpamSet() throws IOException {
        List<Message> notSpamMessages = new ArrayList<Message>();
        notSpamMessages = mail.getMessages("in:Inbox");
        return notSpamMessages;
    }

    public Hashtable<String, Float> setProbForEmail(String query) throws IOException, MessagingException {
        int counter = 0;
        int length = 0;
        int j = 0;
        float pb ;
        int sizeSpamSet = spamSet().size(); //Este métodos duran mucho
        //int sizeNoSpamSet = noSpamSet().size();//Este métodos duran mucho
        String msgs = "";
        boolean find = false;

        if (query.equals("in:Spam")){
            length = sizeSpamSet;
        } else{
            //length = sizeNoSpamSet;
        }

        for (int i= 1; i < length; i++) {
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
            msgs = msgs.replace('=', ' '); msgs = msgs.replace('$', ' ');

            String[] setOfWords = msgs.split(" +");

            for (String s : setOfWords) {
                if (!s.matches("^ +") && !s.equals(" ")) {
                    Word w = new Word(s);
                    if (dictionary.contains(w.getWord())) {
                        if (query.equals("in:Spam")) {
                            w.setFrequencyInSpam(w.getFrequencyInSpam() + 1);
                        } else {
                            w.setFrequencyInNotSpam(w.getFrequencyInNotSpam() + 1);
                        }
                    } else {
                        pb = 0.0F;
                        counter = 0;
                        //for (int j = 0; j < setOfWords.length; ++j) {
                        j = 0;
                        find=false;
                        while(j < setOfWords.length && !find){
                            if (s.equals(setOfWords[j])) {
                                counter++;
                                find = true;
                            }
                            j++;
                        }
                        if (query.equals("in:Spam")) {
                            pb = (float) counter / sizeSpamSet;
                        } else {
                           // pb = (float) counter / sizeNoSpamSet;
                        }
                        dictionary.put(s, pb);
                    }
                }
            }
        }
            return dictionary;
    }

    /*public Hashtable<String, Float> getPuta(String query) throws IOException, MessagingException {
        int sizeSpamSet = spamSet().size();
        int sizeNoSpamSet = noSpamSet().size();
        Hashtable<String, Float> tmp = new Hashtable<String, Float>();
        String body = "";
        int length = 0;

        if (query.equals("in:Spam")){
            length = sizeSpamSet;
        } else{
            length = sizeNoSpamSet;
        }

        for(int i = 1; i > length; i++){
            body = mail.getBody(query, i);
            tmp += this.setProbForEmail(body, query);
        }

        body = mail.getBody(query, length);
        tmp = this.setProbForEmail(body, query);
        return tmp;
    }*/
}