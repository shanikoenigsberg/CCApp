package creditCardFiles;
import java.util.Objects;

public class Address {
	
	String street;
	String city;
	USState usState; 
	String zipCode;
	
	public Address(String street, String city, USState usState, String zipCode) {
		
		this.street = street;
		this.city = city;
		this.usState = usState;
		
		if(zipCode.length() < 5) {
			throw new RuntimeException("Invalid Zip");
		}
		this.zipCode = zipCode;
		
	}

	public Address(Address address) {
		
		this.street = address.getStreet();
		this.city = address.getCity();
		this.usState = address.getUsState();
		this.zipCode = address.getZipCode();
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public USState getUsState() {
		return usState;
	}

	public void setUsState(USState usState) {
		this.usState = usState;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		
		if(zipCode.length() >= 5) {
			this.zipCode = zipCode;
		}
	}

	@Override
	public String toString() {
		
		StringBuilder address = new StringBuilder("\nAddress: ");
		
		address.append("\n" + this.street + "\n" + this.city + ", " + this.usState + " " + this.zipCode);
		
		return address.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true; 
		}
		
		if (obj == null) {
			return false;
		}
		
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		Address other = (Address) obj;
		
		return Objects.equals(street, other.street) && Objects.equals(zipCode, other.zipCode);
	}
	
	
	
	
	
	

}
