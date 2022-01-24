package creditCardFiles;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		String name;
		System.out.print("Hello. What is your name? ");
		name = input.nextLine();
		
		try {
			
			CreditCardApp ourApp = new CreditCardApp(name);
		}
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
		
		
		
	}

}
