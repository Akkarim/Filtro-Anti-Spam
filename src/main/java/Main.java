import javax.mail.MessagingException;
import java.io.IOException;

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
            control.logIn("caca");
        } catch (IOException e){
            System.out.println("Ups! Error. Try again later.");
        }

        //data.store("caca", 0.7, 0.1, 12);

        //control.logOut();
        //control.getMail(1);
        //bayes.noSpamSet(10);
        //System.out.println(control.getBody(gm.getGmailService(), "me", "15fcac93204c778e"));
        String body = gm.getBody("in:Spam");
        //bayes.setProbForEmail(body);

        data.store("caca", 0.9, 0.6, bayes.setProbForEmail(body));
    }

}
