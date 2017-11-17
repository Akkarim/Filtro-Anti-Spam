import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

public class Controller {
    private GmailRetriever mails;
    private String bf;

    public Controller(){

        mails = new GmailRetriever();
    }

    public void configuration(){

    }

    public void train(){

    }

    public String showData(){
        String data = "";
        return data;
    }

    public Message getMail() throws IOException {
        Message mail = new Message();
        List<Message> lista = new List<Message>();
        lista = mails.getMessages();
        mail = lista.get(1);
        System.out.println(mail.getId());
        return mail;
    }

    public static void logOut(){
        String path = System.getProperty("user.home")+ "//.credentials/gmail-java-Bayesian_Filter//StoredCredential";
        File data = new File(path);
        data.delete();
        System.out.println("Your cretentials has been deleted. BITCH!");
    }

    public void logIn(String usr) throws IOException {
        bf = System.getProperty("user.home") + "//Documents/BayesianFilter/Users";
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
