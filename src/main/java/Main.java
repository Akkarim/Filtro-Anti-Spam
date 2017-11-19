import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author Jimmy Acuña, Luis Carvajal
 *
 */

/**
 * Clase que ejecuta el programa completo
 */
public class Main {
    private  static  Controller control;
    private static Interface interf;
    private static GmailRetriever gm;

    /**
     * Constructor de la clase
     */
    public Main(){
        control = new Controller();
        interf = new Interface(control);
        gm = new GmailRetriever();
    }

    /**
     * Llama a la interfaz
     * @throws IOException
     */
    public void start() throws IOException {
        interf.menu();
    }

    /**
     * Ejecuta el programa
     * @param args
     * @throws IOException
     * @throws MessagingException
     */
    public static void main(String[] args) throws IOException, MessagingException {
        Main aMain = new Main();
        BayesianFilter bayes = new BayesianFilter();
        Data data = new Data();
        //aMain.start();

        try {
            control.logIn("usuario");
        } catch (IOException e){
            System.out.println("Ups! Error. Try again later.");
        }
        data.delete("usuario");
        data.store("usuario",0,0.3, 4, bayes.setProbForEmail("in:Spam"));

    }

}
