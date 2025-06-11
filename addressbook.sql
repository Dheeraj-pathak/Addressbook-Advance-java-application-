DROP TABLE IF EXISTS Addresses;

-- Create the Addresses table with Department (Dropdown) and Address (Text)
CREATE TABLE Addresses (
    AddressID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(254) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Department ENUM('Mkt', 'HR', 'Finance', 'Admin') NOT NULL, -- Dropdown values using ENUM in MySQL
    Address TEXT NOT NULL -- Address as text field
);

-- Insert sample data
INSERT INTO Addresses (FirstName, LastName, Email, PhoneNumber, Department, Address)
VALUES 
    ('Mike', 'Green', 'demo1@deitel.com', '555-5555', 'Finance', '123 Green St, New York, NY'),
    ('Mary', 'Brown', 'demo2@deitel.com', '555-1234', 'HR', '456 Brown Ave, Los Angeles, CA');