import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

public class Controller {
    private GmailRetriever mails;
    private String bf;
    private BayesianFilter filter;

    public Controller(){

        mails = new GmailRetriever();
        filter = new BayesianFilter();
    }

    public void configuration(double spamProb, double spamThresh, int setSize){
        filter.setSpamProbab(spamProb);
        filter.setSpamThreshold(spamThresh);
        filter.setSizeOfTrainSet(setSize);
    }

    public void train(){

    }

    public String showData(){
        String data = "";
        return data;
    }

    public Message getMail() throws IOException {
        Message mail = new Message();
        /*List<Message> lista = new List<Message>();
        lista = mails.getMessages();
        mail = lista.get(1);
        System.out.println(mail.getId());*/
        return mail;
    }

    public static void logOut(){
        String path = System.getProperty("user.home")+ "//.credentials/gmail-java-Bayesian_Filter//StoredCredential";
        File data = new File(path);
        data.delete();
        System.out.println("Your cretentials has been deleted. BITCH!");
    }

    public void logIn(String usr) throws IOException {
        bf = System.getProperty("user.home") + "//Documentos/BayesianFilter/Users";
        File bayesFilt = new File(bf);
        if(!bayesFilt.exists()) {
            bayesFilt.mkdir();
        } else{
            File userData = new File(bf+ "//" + usr);
            if(!userData.exists()){
                userData.mkdir();
            }
        }
        mails.getMessages();
    }

}
