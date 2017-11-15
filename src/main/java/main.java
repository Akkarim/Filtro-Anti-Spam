public class Main {
    private  static  Controller control;
    private static Interface interf;

    public Main(){
        control = new Controller();
        interf = new Interface(control);
    }

    public void start(){
        interf.menu();
    }

    public static void main(String[] args){
        Main aMain = new Main();
        aMain.start();
    }

}
