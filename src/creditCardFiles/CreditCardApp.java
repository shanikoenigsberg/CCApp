package creditCardFiles;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class CreditCardApp {

	private String ownerName;
	private CreditCards cards;
	private Scanner input;

	public CreditCardApp(String ownerName) {

		this.ownerName = ownerName;
		this.cards = new CreditCards();
		this.input = new Scanner(System.in);
		System.out.println("Welcome to our Credit Card App, " + ownerName + "!");
		displayMenu();
	}

	private void displayMenu() {

		System.out.println();
		System.out.println("Menu");
		System.out.println("-----------------------------------------------------");

		System.out.print("1. Add a new CreditCard\r\n" + "2. Remove a CreditCard\r\n"
				+ "3. Display total outstanding balance\r\n" + "4. Display total available credit\r\n"
				+ "5. Display largest purchase \r\n" + "6. Display most recent payment\r\n"
				+ "7. Display total spent on certain category of expense\r\n"
				+ "8. For which type of Purchase was the most money spent\r\n" + "9. Manage a specific Credit Card\r\n"
				+ "10. Exit app\r\n");

		System.out.println();
		System.out.print("Your choice: ");
		int choice = input.nextInt();
		input.nextLine();
		while (choice < 1 || choice > 10) {
			System.out.println("Invalid choice.");
			System.out.print("Your choice: ");
			choice = input.nextInt();
			input.nextLine();
		}

		processMenuChoice(choice);

	}

	public void processMenuChoice(int choice) {

		switch (choice) {

		case 1:
			addCreditCard();
			break;

		case 2:
			removeCreditCard();
			break;

		case 3:
			displayOutstandingBalance();
			break;

		case 4:
			displayTotalAvailCredit();
			break;

		case 5:
			displayLargestPurchase();
			break;

		case 6:
			displayRecentPayment();
			break;

		case 7:
			displayTotalOfCategory();
			break;

		case 8:
			displayTypeMaxPurchaseValue();
			break;

		case 9:
			CreditCard c = getCreditCard();
			manageSpecificCard(c);
			break;

		default:
			System.out.print("Thank you for using!");
			System.exit(0);
		}

	}

	public void addCreditCard() {

		String ccNum, issueCompany, type;
		LocalDate issueDate, expDate;
		double creditLimit;

		System.out.print("Enter the Credit Card Num: ");
		ccNum = input.nextLine();

		while (ccNum.length() < 15 || ccNum.length() > 16) {
			System.out.println("Invalid CC Number Length.");
			System.out.print("Please re-enter: ");
			ccNum = input.nextLine();
		}

		System.out.print("Who is issuing you this new card? ");
		issueCompany = input.nextLine();
		while (issueCompany == null || issueCompany.isBlank()) {
			System.out.println("Invalid.");
			System.out.print("Who is issuing you this new card? ");
			issueCompany = input.nextLine();
		}

		System.out.print("Type of Credit Card (AMEX, VISA, MASTERCARD)? ");
		type = input.nextLine().toUpperCase();
		while (!type.equals("AMEX") && !type.equals("VISA") && !type.equals("MASTERCARD")) {
			System.out.println("Invalid type.");
			System.out.print("Type of Credit Card? ");
			type = input.nextLine().toUpperCase();
		}
		CreditCardType ccType = CreditCardType.valueOf(type);

		System.out.print("When was this card issued to you (yyyy-mm-dd)? ");
		issueDate = getDate();
		while(issueDate.isAfter(LocalDate.now())) {
			System.out.println("Date cannot be after current date.");
			System.out.print("Please re-enter: ");
			issueDate = getDate();
		}

		System.out.print("When does this card expire (yyyy-mm-dd)? ");
		expDate = getDate();
		while (expDate.isBefore(issueDate)) {
			System.out.println("Invalid expiration date. Expiration date must be after issue date.");
			System.out.print("When does this card expire (yyyy-mm-dd)? ");
			expDate = getDate();
		}

		System.out.print("What is your credit limit? ");
		creditLimit = input.nextDouble();
		while (creditLimit <= 0) {
			System.out.println("Invalid credit limit.");
			System.out.print("What is your credit limit? ");
			creditLimit = input.nextDouble();
		}
		input.nextLine();

		try {
			CreditCard newCard = new CreditCard(ccNum, issueDate, expDate, issueCompany, ccType, creditLimit);
			cards.addCard(newCard);
			System.out.println("Credit Card added.");
		} catch (CreditCardException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public LocalDate getDate() {
		String date;
		
		//set date to current date if something goes wrong and can't reset to inputed date
		LocalDate localDate = LocalDate.now();
		date = input.nextLine();
		try {
			localDate = LocalDate.parse(date,
					DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
		} catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
			System.out.print("Please re-enter: ");
			getDate();
		}
		return localDate;
	}

	public void removeCreditCard() {

		if (cards.isEmpty()) {
			System.out.println("There are no cards to remove.");
		} else {

			System.out.print("Please enter the Credit Card Number you wish to remove: ");
			String ccNum = input.nextLine();
			while (ccNum.length() < 15 || ccNum.length() > 16) {
				System.out.println("Invalid CC Number length.");
				System.out.print("Please enter the Credit Card Number you wish to remove: ");
				ccNum = input.nextLine();
			}

			try {
				cards.removeCard(ccNum);
				System.out.println("Card Removed.");
			} catch (CreditCardException e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public void displayOutstandingBalance() {
		
		double totalBalance = cards.totalBalance();
		
		if (totalBalance == 0) {
			System.out.println("You have no outstanding balance.");
		} else {
			System.out.println("Total Outstanding Balance: $" + totalBalance);
		}
		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public void displayTotalAvailCredit() {

		System.out.println("Total Available Credit: $" + cards.totalAvailCredit());

		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public void displayLargestPurchase() {
		if (cards.isEmpty()) {
			System.out.println("You have no purchases.");
			
		} else {
			//sorting purchases by amount 
			PriorityQueue<Purchase> purchases = new PriorityQueue<Purchase>(new PurchaseAmountComparator());
			for (CreditCard card : cards) {
				Purchase largestPurchase = card.getLargestPurchase();
				if(largestPurchase == null) {
					continue;
				}
				else {
					purchases.offer(largestPurchase);
				}
			}
			if(purchases.isEmpty()) {
				System.out.println("You have no purchases.");
			}
			else {
				System.out.println("Largest Purchase: " + purchases.peek());
			}
			
		}


		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public void displayRecentPayment() {
		if (cards.isEmpty()) {
			System.out.println("You have no payments.");
			
		} else {
			//sorting payments by date
			PriorityQueue<Payment> payments = new PriorityQueue<Payment>(new PaymentDateComparator());
			
			for (CreditCard card : cards) {
				Payment mostRecent = card.getMostRecentPayment();
				if(mostRecent == null) {
					continue;
				}
				else {
					payments.offer(mostRecent);
				}
			}
			if(payments.isEmpty()) {
				System.out.println("You have no payments.");
			}
			else {
				System.out.println("Most Recent Payment: " + payments.peek());
			}
			
		}

		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public void displayTotalOfCategory() {
		if (cards.isEmpty()) {
			System.out.println("You have no purchases.");
		} else {
			double totalSpent = 0;
			System.out.print(
					"Enter the Category you would like to see (Car, Clothing, Food, Groceries, Lodging, Restaurant, Travel, Utilities, Misc): ");
			String category = input.nextLine().toUpperCase();

			//validate valid enum
			while (!category.equals("CAR") && !category.equals("CLOTHING") && !category.equals("FOOD")
					&& !category.equals("GROCERIES") && !category.equals("LODGING") && !category.equals("RESTAURANT")
					&& !category.equals("TRAVEL") && !category.equals("UTILITES") && !category.equals("MISC")) {
				System.out.println("Invalid Category. ");
				System.out.print("Please re-enter: ");
				category = input.nextLine().toUpperCase();
			}
			PurchaseType purchaseCategory = PurchaseType.valueOf(category);

			for (CreditCard card : cards) {
				Iterator<Purchase> purchaseIterator = card.purchaseIterator();
				while (purchaseIterator.hasNext()) {
					Purchase p = purchaseIterator.next();
					if (p.getPurchaseType().equals(purchaseCategory)) {
						totalSpent += p.getAmount();
					}
				}

			}

			System.out.println("Total Spent On " + category + ": " + totalSpent);
		}
		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();
	}

	public void displayTypeMaxPurchaseValue() {
		if (cards.isEmpty()) {
			System.out.println("You have no purchases.");
		} else {
			//creating a hashmap to hold the type with the amount spent on it
			HashMap<PurchaseType, Double> purchaseAmounts = new HashMap<PurchaseType, Double>();
			purchaseAmounts.put(PurchaseType.CAR, 0.0);
			purchaseAmounts.put(PurchaseType.CLOTHING, 0.0);
			purchaseAmounts.put(PurchaseType.FOOD, 0.0);
			purchaseAmounts.put(PurchaseType.GROCERIES, 0.0);
			purchaseAmounts.put(PurchaseType.LODGING, 0.0);
			purchaseAmounts.put(PurchaseType.RESTAURANT, 0.0);
			purchaseAmounts.put(PurchaseType.TRAVEL, 0.0);
			purchaseAmounts.put(PurchaseType.UTILITIES, 0.0);
			purchaseAmounts.put(PurchaseType.MISC, 0.0);

			for (CreditCard card : cards) {
				// this does not get the actual purchase
				Iterator<Purchase> purchaseIterator = card.purchaseIterator();
				while (purchaseIterator.hasNext()) {
					Purchase p = purchaseIterator.next();
					purchaseAmounts.put(p.getPurchaseType(), purchaseAmounts.get(p.getPurchaseType()) + p.getAmount());
				}
			}
			
			//getting the purchase based on the amount
			Double maxValue = Collections.max(purchaseAmounts.values());
			if (maxValue == 0) {
				System.out.println("You have no purchases.");
			} else {
				for (PurchaseType p : purchaseAmounts.keySet()) {
					if (purchaseAmounts.get(p).equals(maxValue)) {
						System.out.println("Most money spent on: " + p);
						break;
					}

				}
			}

		}
		System.out.println("Press enter to return to main menu");
		input.nextLine();
		displayMenu();

	}

	public CreditCard getCreditCard() {
		CreditCard c = null;
		String ccNum = null;
		if (cards.isEmpty()) {
			System.out.println("You have no cards.");
			System.out.println("Press enter to return to main menu");
			input.nextLine();
			displayMenu();
		} else {
			System.out.print("Enter the Credit Card Num: ");
			ccNum = input.nextLine();

			while (ccNum.length() < 15 || ccNum.length() > 16) {
				System.out.print("Invalid CC Number Length.");
				System.out.print("Please re-enter: ");
				ccNum = input.nextLine();
			}
		}
		try {
			c = cards.findCard(ccNum);
		} catch (CreditCardException e) {
			System.out.println(e.getMessage());
			getCreditCard();
		}
		return c;

	}

	public void manageSpecificCard(CreditCard c) {

		try {

			System.out.println("\nWhat would you like to do with the Credit Card?");
			System.out.println("\t1. Display current balance\n" + "\t2. Display current credit limit\n"
					+ "\t3. Add a Purchase\n" + "\t4. Add a Payment\n" + "\t5. Add a Fee\n"
					+ "\t6. Display most recent Purchase\n" + "\t7. Display most recent Payment\n"
					+ "\t8. Switch credit card status\n" + "\t9. Back to main menu\n");
			int choice;
			System.out.print("Your choice: ");
			choice = input.nextInt();
			input.nextLine();
			while (choice < 1 || choice > 9) {
				System.out.println("Invalid choice.");
				System.out.print("Your choice: ");
				choice = input.nextInt();
				input.nextLine();
			}

			switch (choice) {
			case 1:
				displayCurrBalance(c);
				break;

			case 2:
				displayCreditLimit(c);
				break;

			case 3:
				addPurchase(c);
				break;

			case 4:
				addPayment(c);
				break;

			case 5:
				addFee(c);
				break;

			case 6:
				displayRecentPurchase(c);
				break;

			case 7:
				displayRecentPayment(c);
				break;

			case 8:
				changeStatus(c);
				break;

			default:
				System.out.println("Press enter to return to main menu");
				input.nextLine();
				displayMenu();
				break;

			}
		} catch (CreditCardException e) {
			System.out.println(e.getMessage());
			manageSpecificCard(c);
		}
	}

	public void displayCurrBalance(CreditCard c) {

		System.out.println("Credit Card Num: " + c.getCreditCardID());
		System.out.println("Current Balance: $" + c.getCurrentBalance());

		manageSpecificCard(c);
	}

	public void displayCreditLimit(CreditCard c) {

		System.out.println("Credit Card Num: " + c.getCreditCardID());
		System.out.println("Current Credit Limit: $" + c.getCreditCardLimit());

		manageSpecificCard(c);
	}

	public void addPurchase(CreditCard c) {

		System.out.print(
				"Enter Purchase Type (Car, Clothing, Food, Groceries, Lodging, Restaurant, Travel, Utilities, Misc): ");
		String type = input.nextLine().toUpperCase();

		//validate valid enum
		while (!type.equals("CAR") && !type.equals("CLOTHING") && !type.equals("FOOD") && !type.equals("GROCERIES")
				&& !type.equals("LODGING") && !type.equals("RESTAURANT") && !type.equals("TRAVEL")
				&& !type.equals("UTILITES") && !type.equals("MISC")) {

			System.out.println("Invalid Type. ");
			System.out.print("Please re-enter: ");
			type = input.nextLine().toUpperCase();
		}

		System.out.print("What was the date of the purchase (yyyy-mm-dd)? ");
		LocalDate pDate = getDate();
		
		//date can not be in the future
		while(pDate.isAfter(LocalDate.now())) {
			System.out.println("Date cannot be after current date.");
			System.out.print("Please re-enter: ");
			pDate = getDate();
		}

		System.out.print("Enter Purchase Amount: ");
		double amnt = input.nextDouble();
		
		while (amnt <= 0 || c.getAvailCredit() < amnt) {
			System.out.println("Amount cannot be less than 0 or above available credit.");
			System.out.print("Please re-enter: ");
			amnt = input.nextDouble();
		}
		input.nextLine();
		
		String name, street, city, state, zip;
		System.out.print("Vendor Name: ");
		name = input.nextLine();
		
		while(name == null || name.isEmpty()) {
			System.out.println("Invalid.");
			System.out.print("Vendor Name: ");
			name = input.nextLine();
		}
		
		System.out.print("Vendor Street: ");
		street = input.nextLine();
		while(street == null || street.isEmpty()) {
			System.out.println("Invalid.");
			System.out.print("Please enter vendor street: ");
			street = input.nextLine();
		}
		
		System.out.print("Vendor City: ");
		city = input.nextLine();
		while(city == null || city.isEmpty()) {
			System.out.println("Invalid.");
			System.out.print("Please enter vendor city: ");
			city = input.nextLine();
		}
		
		System.out.print("Vendor State (Abbreviated Format): ");
		state = input.nextLine().toUpperCase();
		
		//validate valid enum using isMember method
		while(!USState.isMember(state)) {
			System.out.println("Invalid State.");
			System.out.print("Please enter vendor state: ");
			state = input.nextLine().toUpperCase();
		}
		USState stateEnum = USState.valueOf(state);
		
		System.out.print("Vendor Zip: ");
		zip = input.nextLine();
		while(zip == null || zip.isBlank() || zip.isEmpty() || zip.length() < 5) {
			System.out.println("Invalid.");
			System.out.print("Please enter vendor zip: ");
			zip = input.nextLine();
		}
		
		
		try {
			cards.addPurchase(c, new Purchase(PurchaseType.valueOf(type), amnt, pDate, new Vendor(name, new Address(street, city, stateEnum, zip))));
			System.out.println("Purchase added.");
		} catch (DuplicateTransactionException | IllegalTransactionException e) {
			System.out.println(e.getMessage());
		}
		//catch for the address
		catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}

		manageSpecificCard(c);

	}

	public void addPayment(CreditCard c) {

		System.out.print("Enter Payment Type (Check, Online): ");
		String type = input.nextLine().toUpperCase();
		while (!type.equals("CHECK") && !type.equals("ONLINE")) {
			System.out.println("Invalid Type.");
			System.out.print("Please re-enter: ");
			type = input.nextLine().toUpperCase();
		}

		System.out.print("Enter Bank Name: ");
		String bankName = input.nextLine();

		System.out.print("Enter Bank Account ID: ");
		String bankID = input.nextLine();

		System.out.print("What was the date of the payment (yyyy-mm-dd)? ");
		LocalDate pDate = getDate();
		
		//date can not be in the future
		while(pDate.isAfter(LocalDate.now())) {
			System.out.println("Date cannot be after current date.");
			System.out.print("Please re-enter: ");
			pDate = getDate();
		}

		System.out.print("Enter Payment Amount: ");
		double amnt = input.nextDouble();
		while (amnt <= 0) {
			System.out.println("Invalid Amount.");
			System.out.print("Please re-enter: ");
			amnt = input.nextDouble();
		}
		input.nextLine();

		try {
			cards.addPayment(c, new Payment(PaymentType.valueOf(type), new BankAccount(bankName, bankID), amnt, pDate));
			System.out.println("Payment added.");
		} catch (DuplicateTransactionException | IllegalTransactionException e) {
			System.out.println(e.getMessage());
		}

		manageSpecificCard(c);

	}

	public void addFee(CreditCard c) {

		System.out.print("Enter Fee Type (Latepayment, Interest): ");
		String type = input.nextLine().toUpperCase();
		while (!type.equals("LATEPAYMENT") && !type.equals("INTEREST")) {
			System.out.println("Invalid Type.");
			System.out.print("Please re-enter: ");
			type = input.nextLine().toUpperCase();
		}

		System.out.print("When was the fee received (yyyy-mm-dd)? ");
		LocalDate fDate = getDate();
		
		//date can not be in the future
		while(fDate.isAfter(LocalDate.now())) {
			System.out.println("Date cannot be after current date.");
			System.out.print("Please re-enter: ");
			fDate = getDate();
		}

		System.out.print("Enter Fee Amount: ");
		double amnt = input.nextDouble();
		while (amnt <= 0) {
			System.out.println("Invalid Amount.");
			System.out.print("Please re-enter: ");
			amnt = input.nextDouble();
		}
		input.nextLine();

		try {
			cards.addFee(c, new Fee(FeeType.valueOf(type), amnt, fDate));
			System.out.println("Fee added.");
		} catch (DuplicateTransactionException | IllegalTransactionException e) {
			System.out.println(e.getMessage());
		}

		manageSpecificCard(c);

	}

	public void displayRecentPurchase(CreditCard c) {
		
		Purchase p = c.getMostRecentPurchase();

		System.out.println("Credit Card Num: " + c.getCreditCardID());

		if (p == null) {
			System.out.println("No purchases.");
		} else {
			System.out.println("Most Recent Purchase: " + p);
			System.out.println();
		}

		manageSpecificCard(c);

	}

	public void displayRecentPayment(CreditCard c) {

		Payment p = c.getMostRecentPayment();

		System.out.println("Credit Card Num: " + c.getCreditCardID());

		if (p == null) {
			System.out.println("No payments.");
		} else {
			System.out.println("Most Recent Payment: " + p);
			System.out.println();
		}
		
		manageSpecificCard(c);
	}

	public void changeStatus(CreditCard c) {

		System.out.print("What is this card's new status (Active, Cancelled, Expired, Lost)? ");
		String status = input.nextLine().toUpperCase();
		while (!status.equals("ACTIVE") && !status.equals("CANCELLED") && !status.equals("EXPIRED")
				&& !status.equals("LOST")) {
			System.out.println("Invalid Status.");
			System.out.print("Please re-enter: ");
			status = input.nextLine();
		}

		//only can change to expired if past current date
		if (status.equals("EXPIRED")) {
			if (c.getExpirationDate().compareTo(LocalDate.now()) > 0) {
				System.out.println("Not expired.");
				System.out.println("Returning to menu...");
				manageSpecificCard(c);
			}
			
		//only can change to active if before expiration date
		} else if (status.equals("ACTIVE")) {
			if (c.getExpirationDate().compareTo(LocalDate.now()) < 0) {
				System.out.println("Not active.");
				System.out.println("Returning to menu...");
				manageSpecificCard(c);
			}
		}

		c.setStatus(CreditCardStatus.valueOf(status));

		manageSpecificCard(c);
	}

}
