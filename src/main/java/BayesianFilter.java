import java.util.Hashtable;

public class BayesianFilter {

    private  GmailRetriever mail;
    private double spamProbab;
    private double spamThreshold;
    private Word word;
    private double wordProb;
    private Hashtable<Word,Double> dictionary;

    public BayesianFilter(){
        dictionary = new Hashtable<Word,Double>();
    }


}
