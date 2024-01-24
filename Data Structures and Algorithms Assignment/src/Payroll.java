//Payroll System Main Class
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import static java.lang.Character.isDigit;

public class Payroll {
    //Variables storing classes
    static TextFile txtFile = new TextFile();
    static EmployeeOperations empOpp = new EmployeeOperations();
    static Scanner input = new Scanner(System.in);
    static Database db = new Database();

    //Variables validating selection
    static boolean isSelectionValid = false;
    static boolean isInProgram = true;
    //Admin login verification variables
    static boolean isAdmin = false;
    static String adminU = "IT@devTeam.org";
    static String adminP = "0Name0921038453";

    //Variables storing information for employee addition
    static int displayMsg;
    static String selection;
    static int num;
    static double numPay;
    static int empNum;
    static Date joinDate = new Date();
    static String date = "";


    public static void main(String[] args) {
        //Calling log in function
        Login();
    }

    //Login function
    public static void Login(){
        //Variables storing login information
        isSelectionValid = false;
        int attempts = 0;
        System.out.println("Welcome to the Payroll Program Login");

        //While the information is false, keep trying to validate user information
        while(isSelectionValid == false){

            //Ask user for their login details
            System.out.println("Please enter your username: ");
            String username = getInput();
            System.out.println("Please enter your password: ");
            String password = getInput();

            //Verify information
            isSelectionValid = db.verifyLoginInfo(username, password);
            if(isSelectionValid == true){
                //If the user is an admin, send them to the admin menu, otherwise send them to the regular menu
                if(username.equals(adminU) && password.equals(adminP)){
                    isAdmin = true;
                }
                else{
                    isAdmin = false;
                }
                Menu(password);
            }
            //If their attempt is incorrect, let them know to try again
            else{
                attempts +=1;
                System.out.println("Please try again. You have " + (3 - attempts) + " attempts left to log in.");
            }
            //If they have gotten 3 attempts wrong, shut down the system
            if (attempts == 3){
                System.out.println("You have had too many attempts. Please get the correct information and try again.");
                isInProgram = false;
                isSelectionValid = true;
                Menu(password);
            }
        }
    }

    //Menu function
    public static void Menu(String password) {
        while(isInProgram == true) {
            //If they are admins, show them the admin menu
            if (isAdmin == true) {
                System.out.println("\nMenu\nInput the letter of the operation you want to do (ex. n)\n1. Enter n to add an employee" +
                        "\n2. Enter m to update employees information\n3. Enter d to delete employee information\n4. Enter p to print" +
                        " employee pay stub\n5. Enter r to search for employee record\n6. Enter l to display employee list\n7. Enter" +
                        " q to quit\nNote that the program will only check the first input so if you enter nm, it will enable you to" +
                        " add an employee\nPlease enter the command you would like to execute:");

                //Verify selection
                isSelectionValid = false;
                displayMsg = 0;
                while (isSelectionValid == false) {
                    selection = input.next().toLowerCase();

                    //Based on the letter input, call the correct function
                    switch (selection) {
                        case "n":
                            //Add employee
                            empNum = getEmployeeNum();
                            Date date = getDate();
                            System.out.println("Please Enter Employee Name: ");
                            String name = getInput();
                            System.out.println("Please Enter Employee Address: ");
                            String address = getInput();
                            int phoneNum = getPhoneNum();
                            System.out.println("Please Enter Employee Designation: ");
                            String des = getInput();
                            String grade = getGrade();
                            double basePay = getBasePay();
                            double travelAllowance = getTravelAllowance();
                            double overtimeRate = getOvertimeRate();
                            double loan = getLoan();
                            empOpp.addEmployee(empNum, date.toString(), name, address, phoneNum, des, grade, basePay, travelAllowance,
                                    overtimeRate, loan);
                            pressEnter();
                            isSelectionValid = true;
                            break;
                        case "m":
                            //Modify employee
                            empNum = getEmployeeNum();
                            empOpp.modifyEmployee(empNum);
                            pressEnter();
                            isSelectionValid = true;
                            break;
                        case "d":
                            //Delete employee
                            empNum = getEmployeeNum();
                            empOpp.deleteEmployee(empNum);
                            pressEnter();
                            isSelectionValid = true;
                            break;
                        case "p":
                            //Print employee pay stub
                            empNum = getEmployeeNum();
                            empOpp.printEmployeePayStub(empNum);
                            isSelectionValid = true;
                            break;
                        case "r":
                            //Print specific employee's information
                            empNum = getEmployeeNum();
                            Employee employee = empOpp.findEmployee(empNum);
                            if (employee != null) {
                                employee.displayInfo();
                            } else {
                                System.out.println("The employee does not exist");
                                pressEnter();
                            }
                            isSelectionValid = true;
                            break;
                        case "l":
                            //Print all employee information
                            empOpp.printEmployees();
                            pressEnter();
                            isSelectionValid = true;
                            break;
                        case "q":
                            //Quit the system
                            System.out.println("Goodbye. Hope to see you again soon!");
                            isInProgram = false;
                            isSelectionValid = true;
                            Menu("Q");
                            break;
                        default:
                            displayMsg = 1;
                            if (displayMsg == 1) {
                                System.out.println("Please enter a valid value, as stated above.");
                            }
                            break;
                    }
                }

            }
            //Print the regular menu
            else{
                System.out.println("\nMenu\nInput the letter of the operation you want to do (ex. n)\n1. Enter p to print" +
                        " your pay stub\n2. Enter q to quit\nNote that the program will only check the first " +
                        "input so if you enter pm, it will enable you to print your pay stub\nPlease enter the command you " +
                        "would like to execute:");
                isSelectionValid = false;
                displayMsg = 0;

                //Validate selection
                while (isSelectionValid == false) {
                    selection = input.next().toLowerCase();
                    String id = "";
                    switch (selection) {
                        case "p":
                            //Print the pay stub of the employee logged in
                            for(int i = 0; i < password.length(); i++)
                            {
                                if(isDigit(password.charAt(i)) == false){
                                    i = password.length() + 1;
                                }
                                else{
                                    id += password.charAt(i);
                                }
                            }
                            empNum = Integer.parseInt(id);
                            empOpp.printEmployeePayStub(empNum);
                            isSelectionValid = true;
                            break;
                        case "q":
                            //Quit the system
                            System.out.println("Goodbye. Hope to see you again soon!");
                            isInProgram = false;
                            isSelectionValid = true;
                            Menu("q");
                            break;
                        default:
                            displayMsg = 1;
                            if (displayMsg == 1) {
                                System.out.println("Please enter a valid value, as stated above.");
                            }
                            break;
                    }
                }
            }
        }
    }

    //Validate number input
    public static boolean validateNum() {
        //If the input is a number, store it
        try {
            num = Integer.parseInt(input.nextLine());
        }
        catch (Exception e) {
            num = 0;
        }
        boolean validNum;
        //If the input is not a number, ask the user for a number
        if(num != 0){
            validNum = true;
        }
        else{
            validNum = false;
            System.out.println("Please enter a valid whole number");
        }
        return validNum;
    }

    //Validate payment input
    public static boolean validatePay(boolean validNum) {
        //If the input is a number, store it
        try {
            numPay = Double.parseDouble(input.nextLine());
        }
        catch (Exception e) {
            validNum = false;
            numPay = 0.00;
        }
        //Round the number to 2 decimal places
        if (numPay != 0.00) {
            validNum = true;
            numPay = Math.round(numPay * 100.00)/ 100.00;
        } else {
            System.out.println("Please enter a valid number");

        }
        return validNum;
    }
    //Getting the employee registration number
    public static int getEmployeeNum(){
        Boolean validNum = false;
        Boolean newNum = false;
        num = 0;
        switch (selection) {
            //Based on the function, display the correct message
            case "n":
                System.out.println("Enter Employee Registration Number: ");
                break;
            case "m":
                System.out.println("Enter Employee Registration Number you would like to modify: ");
                break;
            case "d":
                System.out.println("Enter Employee Registration Number you would like to delete: ");
                break;
            case "r":
                System.out.println("Enter Employee Registration Number of the record you would like to view: ");
                break;
            default:
                System.out.println("Enter Employee Registration number you would like to perform the operation with: ");
                break;
        }
        //If it is not a new number when adding an employee, let the user know that
        while (newNum == false) {
            if (txtFile.findInFile(num) != -1 && num != 0 && selection.equals("n")) {
                System.out.println("Employee registration number already exists. Please try again:");
                num = 0;
                validNum = false;
            }
            else if (num != 0){
                newNum = true;
            }
            while (validNum == false) {
                validNum = validateNum();
            }
        }

        return num;
    }

    //Get joining date
    public static Date getDate(){
        //Store date format
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        Boolean validDate = false;
        System.out.println("Enter the Date the Employee Joined (Format dd/MM/yyyy, ex. 02/16/2023): ");
        //Check for correct user date input
        while (validDate == false) {
            date = input.next();
            //If the format is correct, store the date, otherwise let the user know
            try {
                joinDate = DateFor.parse(date);
                validDate = true;
            }
            catch (Exception e) {
                validDate = false;
                System.out.println("Please Enter a Proper Date (Format dd/MM/yyyy, ex. 02/16/2023).");
            }
        }
        return joinDate;
    }
    //Getting the user input
    public static String getInput(){
        //Storing the user input
        String inp = "";
        while(inp == ""){
            inp = input.nextLine();
        }
        return inp;
    }
    //Getting the user phone number
    public static int getPhoneNum(){
        //Variables to check the phone number
        Boolean validNum = false;
        num = 0;
        System.out.println("Enter Employee Phone Number (With no spaces or dashes, ex. 05321984235): ");
        validNum = validateNum();
        //If the phone number is a valid number meets the criteria, store it
        while (validNum == false || (String.valueOf(num).length() <= 3 || String.valueOf(num).length() >= 13)){
            if((String.valueOf(num).length() <= 3 || String.valueOf(num).length() >= 14)){
                System.out.println("Please enter a number with the correct amount of digits (between 4 and 13)");
            }
            validNum = validateNum();
        }
        return num;
    }

    //Getting the employee grade
    public static String getGrade(){
        //Variables verifying employee grade
        String grade = "";
        Boolean validSel = false;
        System.out.println("Please Enter Employee Pay Grade (I, II, III , IV): ");
        //If it is in the valid format, store the grade, otherwise let them know
        while (validSel == false){
            grade = input.nextLine();
            if(grade.toLowerCase().equals("i") || grade.toLowerCase().equals("ii") || grade.toLowerCase().equals("iii") ||
                    grade.toLowerCase().equals("iv")){
                validSel = true;
            }
            else{
                validSel = false;
                System.out.println("Please Enter a Grade in the Correct Format (I, II, III, IV): ");
            }
        }
        return grade;
    }

    //Get base pay
    public static double getBasePay(){
        //Store the base pay if it is in the right format
        Boolean validNum = false;
        numPay = 0;
        System.out.println("Enter Employee Base Pay (Just the Number, no Currency Symbol): ");
        while (validNum == false){
            validNum = validatePay(validNum);
        }
        return numPay;
    }

    //Get travel allowance
    public static double getTravelAllowance(){
        //Store the travel allowance if it is in the right format
        Boolean validNum = false;
        numPay = 0;
        System.out.println("Enter Employee Travel Allowance (Just the Number, no Currency Symbol): ");
        while (validNum == false){
            validNum = validatePay(validNum);
        }
        return numPay;
    }

    //Get over time rate
    public static double getOvertimeRate(){
        //Store the over time rate if it is in the right format
        Boolean validNum = false;
        numPay = 0;
        System.out.println("Enter Employee Overtime Rate (Just the Number, no Currency Symbol): ");
        while (validNum == false){
            validNum = validatePay(validNum);
        }
        return numPay;
    }

    //Get loan
    public static double getLoan(){
        //Store the loan if it is in the right format
        Boolean validNum = false;
        numPay = 0;
        System.out.println("Enter Employee Loan (Just the Number, no Currency Symbol): ");
        while (validNum == false){
            validNum = validatePay(validNum);
        }
        return numPay;
    }

    //Get over time hours
    public static int getOverTimeHours(){
        //Store the over time hours worked if it is in the right format
        Boolean validNum = false;
        num = 0;
        System.out.println("Enter Hours Worked of Overtime: ");
        while (validNum == false){
            validNum = validateNum();
        }
        return num;
    }

    //Get month
    public static int getMonth(){
        //Store the month if it is in the right format
        Boolean validMonth = false;
        num = 0;
        System.out.println("Enter Month Number (Between 1-12): ");
        while (validMonth == false){
            try {
                num = Integer.parseInt(input.next());
            }
            catch (NumberFormatException var1) {
                num = 0;
            }
            for (int i = 1; i <= 12; i++){
                if(num == i){
                    validMonth = true;
                }
            }
            if(validMonth == false){
                System.out.println("Please enter a valid month number (between 1 - 12)");
            }
        }
        return num;
    }

    //Wait for enter to advance
    public static void pressEnter() {
        //If the user presses enter they can continue
        System.out.println("Press Enter to Continue");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}