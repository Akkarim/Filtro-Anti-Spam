import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Hashtable;

public class Main {
    private  static  Controller control;
    private static Interface interf;
    private static GmailRetriever gm;


    public Main(){
        control = new Controller();
        interf = new Interface(control);
        gm = new GmailRetriever();
    }

    public void start() throws IOException {
        interf.menu();
    }

    public static void main(String[] args) throws IOException, MessagingException {
        Main aMain = new Main();
        BayesianFilter bayes = new BayesianFilter();
        Data data = new Data();
        //aMain.start();
        try {
            control.logIn("jimmy");
        } catch (IOException e){
            System.out.println("Ups! Error. Try again later.");
        }

        //data.store("caca", 0.7, 0.1, 12);

        //control.logOut();
        //control.getMail(1);
        //bayes.noSpamSet(10);
        //System.out.println(control.getBody(gm.getGmailService(), "me", "15fcac93204c778e"));
        //String body = gm.getBody("in:Inbox");
        //System.out.println(body);

        //Hashtable<String, Double> table = bayes.setProbForEmail(body);
        data.delete("jimmy");
        //data.store("jimmy",0,0.3, 4, bayes.setProbForEmail(body, "in:Spam"));
        System.out.print(gm.getBody("in:Inbox"));

    }

}
