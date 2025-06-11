package model;

public class Person {
    private int addressID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String department; // Mkt, HR, Finance, Admin

    // Allowed department values
    private static final String[] VALID_DEPARTMENTS = {"Mkt", "HR", "Finance", "Admin"};

    // Default constructor
    public Person() {}

    // Parameterized constructor
    public Person(int addressID, String firstName, String lastName, 
                  String email, String phoneNumber, String address, String department) {
        this.addressID = addressID;
        this.firstName = firstName;
        this.lastName = lastName;
        setEmail(email); // Ensure email validation
        setPhoneNumber(phoneNumber); // Validate phone number
        this.address = address;
        setDepartment(department); // Validate department
    }

    // Getter and Setter Methods
    public void setAddressID(int addressID) { this.addressID = addressID; }
    public int getAddressID() { return addressID; }

    public void setFirstName(String firstName) { 
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName; 
        } else {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
    }
    public String getFirstName() { return firstName; }

    public void setLastName(String lastName) { 
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName; 
        } else {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
    }
    public String getLastName() { return lastName; }

    public void setEmail(String email) { 
        if (email != null && email.length() <= 254 && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email: it must contain '@' and be 254 characters or less.");
        }
    }
    public String getEmail() { return email; }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.matches("\\d{10,15}")) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number. It must contain 10-15 digits.");
        }
    }
      
    public String getPhoneNumber() { return phoneNumber; }

    public void setAddress(String address) { 
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
    }
    public String getAddress() { return address; }

    public void setDepartment(String department) { 
        if (department != null && !department.trim().isEmpty()) {
            for (String validDept : VALID_DEPARTMENTS) {
                if (validDept.equalsIgnoreCase(department)) {
                    this.department = department;
                    return;
                }
            }
            throw new IllegalArgumentException("Invalid department. Choose from: Mkt, HR, Finance, Admin.");
        } else {
            throw new IllegalArgumentException("Department cannot be null or empty.");
        }
    }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
