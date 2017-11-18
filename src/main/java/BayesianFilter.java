import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import java.util.List;

public class BayesianFilter {

    private  GmailRetriever mail;
    private double spamProbab;
    private double spamThreshold;
    private int sizeOfTrainSet;
    private Word word;
    private double wordProb;
    private Hashtable<Word,Double> dictionary;

    public BayesianFilter(){
        dictionary = new Hashtable<Word,Double>();
        mail = new GmailRetriever();
        spamProbab = 0.3;
        spamThreshold = 0.9;
        sizeOfTrainSet = 0;
        word = new Word();
    }

    public void setSpamProbab(double spamProbab) {

        this.spamProbab = spamProbab;
    }

    /*public void setWord(Word word) {

        this.word = word;
    }*/

    public void setSpamThreshold(double spamThreshold) {

        this.spamThreshold = spamThreshold;
    }

    public void addToDictionary(Word w, Double prob){

        dictionary.put(w,prob);
    }

    public void setSizeOfTrainSet(int newSize){

        sizeOfTrainSet = newSize;
    }

    /*public List<Message> spamSet() throws IOException {
        List<Message> spamMessages = new ArrayList<Message>();
        spamMessages = mail.getMessages("in:Spam");
        return spamMessages;
    }

    public List<Message> noSpamSet(long numMessages) throws IOException {
        List<Message> notSpamMessages = new ArrayList<Message>();
        notSpamMessages = mail.getMessages("in:Inbox");
        return notSpamMessages;
    }*/

    public Hashtable<Word, Double> setProbForEmail(String msgs){
        msgs = msgs.replace('.',' ');
        msgs = msgs.replace('•',' ');
        msgs = msgs.replace(',',' ');
        msgs = msgs.replace('?',' ');
        msgs = msgs.replace('¿',' ');
        msgs = msgs.replace('!',' ');
        msgs = msgs.replace('¡',' ');
        msgs = msgs.replace('"',' ');
        msgs = msgs.replace('@',' ');
        msgs = msgs.replace('©',' ');
        msgs = msgs.replace('#',' ');
        msgs = msgs.replace(':',' ');
        msgs = msgs.replace(';',' ');
        word.setWord(msgs);
        String[] setOfWord = word.getWord().split(" +");
        for (String s: setOfWord) {
            Word w = new Word();
            w.setWord(s);
            //System.out.println(s);
            if(dictionary.contains(w)){
                w.setFrequencyInSpam(w.getFrequencyInSpam()+1);
            } else{
                dictionary.put(w,0.9);
            }
        }
        return dictionary;
    }

}
