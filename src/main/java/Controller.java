public class Controller {
    GmailRetriever mails;

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

    public GmailRetriever getMail() {
        GmailRetriever mail = getMail();
        return mail;
    }

    public void logIn (String usuario){

       mails.setUsuario(usuario);
    }

    public void logOut(){

    }


}
