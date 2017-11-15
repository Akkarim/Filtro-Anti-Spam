public class Controller {
    BayesianFilter filter;
    GmailRetriever mails;

    Controller(){
        mails = new GmailRetriever();
    }

    public void configuration(){

    }

    public void logIn (String usuario){

       mails.setUsuario(usuario);
    }

}
