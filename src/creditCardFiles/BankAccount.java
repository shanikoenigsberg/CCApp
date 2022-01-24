package creditCardFiles;

import java.util.Objects;

public class BankAccount {
	private String bankName;
	private String accountID;
	
	public BankAccount(String bankName, String accountID) {
		this.bankName = bankName;
		this.accountID = accountID;
	}

	public String getBankName() {
		return bankName;
	}


	public String getAccountID() {
		return accountID;
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return Objects.equals(accountID, other.accountID) && Objects.equals(bankName, other.bankName);
	}
	
	@Override
	public String toString() {
		return "Bank Name: " + bankName +"\nAccountID: " + accountID;
	}
	
	
	
}
