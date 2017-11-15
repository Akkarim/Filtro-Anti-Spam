import java.util.Hashtable;

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

    public void setWord(Word word) {
        this.word = word;
    }

    public void setSpamThreshold(double spamThreshold) {
        this.spamThreshold = spamThreshold;
    }

    public void addToDictionary(Word w, Double prob){
        dictionary.put(w,prob);
    }
}
