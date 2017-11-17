import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;



public class Controller {
    private GmailRetriever mails;
    private String bf;
    private BayesianFilter filter;

    public Controller(){

        mails = new GmailRetriever();
        filter = new BayesianFilter();
        bf = "";
    }

    public void configuration(double spamProb, double spamThresh, int setSize){
        filter.setSpamProbab(spamProb);
        filter.setSpamThreshold(spamThresh);
        filter.setSizeOfTrainSet(setSize);
    }


    public void train(){
        int counter = 42;

    }

    public String showData(){
        String data = "";
        return data;
    }

    public Message getMail(int num) throws IOException { // @param: número de correo
        List<Message> array = new ArrayList<Message>();

        array = mails.getMessages("In:Inbox");// puede ser in:Spam o in:Inbox
        if (array.get(num)  != null){
            System.out.println(array.get(num));
            return array.get(num);
        } else {
            return null;
        }
    }

    public static void logOut(){
        String path = System.getProperty("user.home")+ "//.credentials/gmail-java-Bayesian_Filter//StoredCredential";
        File data = new File(path);
        data.delete();
        System.out.println("Your cretentials has been deleted.");
    }

    public void logIn(String usr) throws IOException {
        bf = System.getProperty("user.home") + "//Documents/BayesianFilter//Users" + "//" + usr; //
        File bayesFilt = new File(bf);
        bayesFilt.mkdirs();
        //mails.getMessages();
        System.out.println(bf);
    }

    public static String getBody(Gmail service, String userId, String messageId)
            throws IOException {
        Message message = service.users().messages().get(userId, messageId).execute();
        String body = message.getSnippet();

        return body;
    }
}
