import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import java.util.List;

public class BayesianFilter {

    private  GmailRetriever mail;
    private Email email;
    private double spamProbab;
    private double spamThreshold;
    private int sizeOfTrainSet;
    private Word word;
    private double wordProb;
    private Hashtable<String,Double> dictionary;

    public BayesianFilter(){
        dictionary = new Hashtable<String,Double>();
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

    /*public void setWord(Word word) {

        this.word = word;
    }*/

    public void setSpamThreshold(double spamThreshold) {

        this.spamThreshold = spamThreshold;
    }

    public void addToDictionary(String s, Double prob){

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

    public Hashtable<String, Double> setProbForEmail(String msgs){
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
        msgs = msgs.replace('0',' ');
        msgs = msgs.replace('1',' ');
        msgs = msgs.replace('2',' ');
        msgs = msgs.replace('3',' ');
        msgs = msgs.replace('4',' ');
        msgs = msgs.replace('5',' ');
        msgs = msgs.replace('6',' ');
        msgs = msgs.replace('7',' ');
        msgs = msgs.replace('8',' ');
        msgs = msgs.replace('9',' ');
        msgs = msgs.replace('(',' ');
        msgs = msgs.replace(')',' ');
        msgs = msgs.replace('-',' ');
        msgs = msgs.replace('_',' ');
        msgs = msgs.replace('|',' ');
        msgs = msgs.replace('/',' ');
        //word.setWord(msgs);
        //String[] setOfWord = word.getWord().split(" +");
        email.setBody(msgs);    //VER SI VA AQUI
        String[] setOfWords = msgs.split(" +");

        for (String s: setOfWords) {
            Word w = new Word(s);
            if(dictionary.contains(w.getWord())){
                w.setFrequencyInSpam(w.getFrequencyInSpam()+1);
            } else{
                int counter = 0;
                double pb = 0.0;
                int start = 0;
                int end = email.getBody().indexOf('\245');
                while (end < setOfWords.length && end > 0) {
                    String part = email.getBody().substring(start, end);
                    if(part.indexOf(s) > 0){
                        counter++;
                    }
                    start = end+1;
                    String prb = email.getBody().substring(end, email.getBody().length());
                    end = prb.indexOf('\245');
                }
                try {
                    pb = counter/spamSet().size();
                } catch (IOException e){
                    System.out.println("Error" + e);
                }
                System.out.println(s);
                dictionary.put(s,pb);
            }
        }
        return dictionary;
    }

}