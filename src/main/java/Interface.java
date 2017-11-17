import java.io.IOException;
import java.util.Scanner;

public class Interface {

	private Controller controller;

	public Interface(Controller control) {
		controller = control;
	}


	public void menu() throws IOException {
		System.out.println("Welcome, please select an option.");
		System.out.print(" Option 1: Authenticate yourself \n Option 2: Exit \n");
		Scanner opt = new Scanner(System.in);
		int option = opt.nextInt();
		switch (option){

			case 1 :
				//el usuario debe logearse primero

				System.out.println("Please select an option.");
				System.out.print(" Option 1: Configure \n Option 2: Train \n Option 3: Show Data \n Option 4: Get new mail \n Option 5: Log Out \n Option 6: Exit \n");
				Scanner op = new Scanner(System.in);
				int option2 = op.nextInt();
				switch (option2){
					case 1:
                        System.out.println("Please enter the spam probability");
						Scanner prob = new Scanner(System.in);
						double spamProb = prob.nextDouble();

                        System.out.println("Please enter the spam threshold");
                        Scanner thres = new Scanner(System.in);
                        double trhesholdProb = prob.nextDouble();

                        System.out.println("Please enter size of the training set");
                        Scanner size = new Scanner(System.in);
                        int trainSet = prob.nextInt();
						controller.configuration(spamProb,trhesholdProb,trainSet);
						break;

					case 2: controller.train();
						break;

					case 3: controller.showData();
						break;

					case 4: controller.getMail(0); //arreglar esta picha
						break;

					case 5: controller.logOut();
						break;

					case 6: System.exit(1);
						break;

					default: System.out.println("Please select a valid option.");
						menu();
						break;
				}

			case 2: System.exit(1);
				break;

			default: System.out.println("Please select a valid option.");
				menu();
				break;
		}

	}
}
