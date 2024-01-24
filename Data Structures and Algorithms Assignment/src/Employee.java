//Employee class
public class Employee {
    //Variables storing employee information
    private int regNum;
    private String joinDate;
    private String name;
    private String address;
    private int phoneNum;
    private String designation;
    private String grade;
    private double basePay;
    private double travelAllowance;
    private double overtimeRate;
    private double loan;

    //Creating an employee
    public Employee(int regNum, String joinDate, String name, String address, int phoneNum, String designation, String grade,
                    double basePay, double travelAllowance, double overtimeRate, double loan){
        this.regNum = regNum;
        this.joinDate = joinDate;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.designation = designation;
        this.grade = grade;
        this.basePay = basePay;
        this.travelAllowance = travelAllowance;
        this.overtimeRate = overtimeRate;
        this.loan = loan;
    }

    //Getting employee registration number
    public int getRegNum() {
        return regNum;
    }

    //Setting employee registration number
    public void setRegNum(int regNum) {
        this.regNum = regNum;
    }

    //Getting employee join date
    public String getJoinDate() {
        return joinDate;
    }

    //Setting employee join date
    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    //Getting employee name
    public String getName() {
        return name;
    }

    //Setting employee name
    public void setName(String name) {
        this.name = name;
    }

    //Getting employee address
    public String getAddress() {
        return address;
    }

    //Setting employee address
    public void setAddress(String address) {
        this.address = address;
    }

    //Getting employee phone number
    public int getPhoneNum() {
        return phoneNum;
    }

    //Setting employee phone number
    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    //Getting employee designation
    public String getDesignation() {
        return designation;
    }

    //Setting employee designation
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    //Getting employee grade
    public String getGrade() {
        return grade;
    }

    //Setting employee grade
    public void setGrade(String grade) {
        this.grade = grade;
    }

    //Getting employee base pay
    public double getBasePay() {
        return basePay;
    }

    //Setting employee base pay
    public void setBasePay(double basePay) {
        this.basePay = basePay;
    }

    //Getting employee travel allowance
    public double getTravelAllowance() {
        return travelAllowance;
    }

    //Setting employee travel allowance
    public void setTravelAllowance(double travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    //Getting employee overtime rate
    public double getOvertimeRate() {
        return overtimeRate;
    }

    //Setting employee overtime rate
    public void setOvertimeRate(double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    //Getting employee loan
    public double getLoan() {
        return loan;
    }

    //Setting employee loan
    public void setLoan(double loan) {
        this.loan = loan;
    }

    //Calculating employee pay stub
    public void calcPayStub(int month, double hours){
        //Calculating variables in employee pay stub
        double houseAllowance = Math.round(basePay * 0.05 * 100.00) / 100.00;
        double healthAllowance = Math.round(basePay * 0.08 * 100.00) / 100.00;
        double overtimePay = Math.round(overtimeRate * hours * 100.00) / 100.00;
        double grossSalary = Math.round(basePay + overtimePay * 100.00) / 100.00;
        double deductions = Math.round(grossSalary * 0.2 * 100.00) / 100.00;
        double netPay = Math.round((grossSalary - deductions - loan) * 100.00) / 100.00;
        String m = "";

        //Determining the month of the pay stub
        switch (month){
            case 1:
                m = "January";
                break;
            case 2:
                m = "February";
                break;
            case 3:
                m = "March";
                break;
            case 4:
                m = "April";
                break;
            case 5:
                m = "May";
                break;
            case 6:
                m = "June";
                break;
            case 7:
                m = "July";
                break;
            case 8:
                m = "August";
                break;
            case 9:
                m = "September";
                break;
            case 10:
                m = "October";
                break;
            case 11:
                m = "November";
                break;
            case 12:
                m = "December";
                break;
        }

        //Printing the employee pay stub
        System.out.println(name + "'s Pay Stub for the Month of " + m + "\nGross Salary: " + grossSalary + "\nHouse Allowance: "
                + houseAllowance + "\nHealth Allowance: " + healthAllowance + "\nTravel Allowance: " + travelAllowance +
                "\nDeductions: " + deductions + "\nNet Pay: " + netPay);
        System.out.println("Press Enter to Continue");
        try{
            System.in.read();
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }

    //Displaying employee info
    public void displayInfo(){
        //Printing the information of the employee
        System.out.println(name + "'s Record\nRegistration Number: " + regNum + "\nJoin Date: " + joinDate + "\nAddress: " +
                address + "\nPhone Number: " + phoneNum + "\nDesignation: " + designation + "\nGrade: " + grade + "\nBase Pay: " +
                basePay + "\nTravel Allowance: " + travelAllowance + "\nOvertime Rate: " + overtimeRate + "\nLoan: " + loan);
        System.out.println("Press Enter to Continue");
        try{
            System.in.read();
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }

}
