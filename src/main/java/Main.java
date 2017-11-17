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

    public static void main(String[] args) throws IOException {
        Main aMain = new Main();
        Data prb = new Data();
        //aMain.start();
        try {
            control.logIn("caca");
        } catch (IOException e){
            System.out.println("Ups! Error. Try again later.");
        }
        //control.logOut();
       // control.getMail(0);

        prb.store("caca", 0.0, 0.0, 0);
        prb.load("caca");
    }

}