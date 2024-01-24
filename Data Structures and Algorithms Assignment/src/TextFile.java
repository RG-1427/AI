//Text File Class
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TextFile {
    //Text file name
    private String fileName = "Payroll File.txt";

    //Creating the text file if it does not exist
    public TextFile(){
        try {
            File payrollFile = new File(fileName);
            if (payrollFile.createNewFile()) {
                System.out.println("The file: " + payrollFile.getName() + " has been created.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Adding employee
    public void add(Employee emp){
        //Writing out the information of the new employee into the file
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            BufferedWriter buffWriter = new BufferedWriter(myWriter);
            buffWriter.write(emp.getRegNum() + "," + emp.getJoinDate() + "," + emp.getName() + "," + emp.getAddress() + "," +
                    emp.getPhoneNum() + "," + emp.getDesignation() + "," + emp.getGrade() + "," + emp.getBasePay() + "," +
                    emp.getTravelAllowance() + "," + emp.getOvertimeRate() + "," + emp.getLoan());
            buffWriter.newLine();
            buffWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when trying to add a new employee into the file.");
            e.printStackTrace();
        }
    }

    //Modifying employee
    public Employee modify(int empNum){
        //Variables needed to modify an employee
        int editLoc = findInFile(empNum);
        List<Employee> empList = getEmp();
        Employee editedEmp = null;

        if(editLoc >= 0) {
            //Seeing what the user wants to modify in the employee
            boolean validChoice = false;
            System.out.println("\nModification Options\n1. Employee Registration Number\n2. Joining Date\n3. Name\n4. Address\n5. " +
                    "Phone Number\n6. Designation\n7. Grade\n8. Travelling Allowance\n9. Loan\n10. Salary\n11. Overtime Rate" +
                    "\nPlease enter the first letter of the option you would like to choose!");

            //Based on the choice, call the right function
            while (validChoice == false) {
                String choice = Payroll.input.next().toLowerCase();
                switch (choice) {
                    case "e":
                        //Change the employee registration number
                        Payroll.selection = "n";
                        int employeeNum = Payroll.getEmployeeNum();
                        empList.get(editLoc).setRegNum(employeeNum);
                        validChoice = true;
                        break;
                    case "j":
                        //Change the employee join date
                        Date date = Payroll.getDate();
                        empList.get(editLoc).setJoinDate(date.toString());
                        validChoice = true;
                        break;
                    case "n":
                        //Change the employee name
                        System.out.println("Please Enter Employee Name: ");
                        String name = Payroll.getInput();
                        empList.get(editLoc).setName(name);
                        validChoice = true;
                        break;
                    case "a":
                        //Change the employee address
                        System.out.println("Please Enter Employee Address: ");
                        String address = Payroll.getInput();
                        empList.get(editLoc).setAddress(address);
                        validChoice = true;
                        break;
                    case "p":
                        //Change the employee phone number
                        int phoneNum = Payroll.getPhoneNum();
                        empList.get(editLoc).setPhoneNum(phoneNum);
                        validChoice = true;
                        break;
                    case "d":
                        //Change the employee designation
                        System.out.println("Please Enter Employee Designation: ");
                        String des = Payroll.getInput();
                        empList.get(editLoc).setDesignation(des);
                        validChoice = true;
                        break;
                    case "g":
                        //Change user grade
                        String grade = Payroll.getGrade();
                        empList.get(editLoc).setGrade(grade);
                        validChoice = true;
                        break;
                    case "t":
                        //Change user travel allowance
                        double travelAllowance = Payroll.getTravelAllowance();
                        empList.get(editLoc).setTravelAllowance(travelAllowance);
                        validChoice = true;
                        break;
                    case "l":
                        //Change user loan
                        double loan = Payroll.getLoan();
                        empList.get(editLoc).setLoan(loan);
                        validChoice = true;
                        break;
                    case "s":
                        //Change user base pay
                        double basePay = Payroll.getBasePay();
                        empList.get(editLoc).setBasePay(basePay);
                        validChoice = true;
                        break;
                    case "o":
                        //Change user overtime rate
                        double overtimeRate = Payroll.getOvertimeRate();
                        empList.get(editLoc).setOvertimeRate(overtimeRate);
                        validChoice = true;
                        break;
                    default:
                        System.out.println("Please enter a valid choice (e, j, n, a, p, d, g, t, l, s, o)");
                        break;
                }
                //Find the edited employee
                editedEmp = empList.get(editLoc);
            }
        }
        //Returning edited employee
        return editedEmp;
    }

    //Deleting employee
    public void delete(int empNum){
        //Finding the employee, deleting them from the file, and rewriting the file without them
        int delLoc = findInFile(empNum);
        if(delLoc >= 0) {
            List<Employee> employeeList = getEmp();
            employeeList.remove(delLoc);
            fileRewrite(employeeList);
        }
    }

    //Get employee list
    public List<Employee> getEmp(){
        //Creating a variable to store the list
        List<Employee> emp = new ArrayList<>();
        try {
            //While there is another line, add the line into the list by creating an employee with that information
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                List<String> info = List.of(data.split(","));
                Employee cur = new Employee(Integer.parseInt(info.get(0)), info.get(1), info.get(2),
                        info.get(3), Integer.parseInt(info.get(4)), info.get(5),
                        info.get(6), Double.parseDouble(info.get(7)), Double.parseDouble(info.get(8)),
                        Double.parseDouble(info.get(9)), Double.parseDouble(info.get(10)));
                emp.add(cur);
            }
            reader.close();
        }
        catch(Exception e){
                e.printStackTrace();
            }
        //Return employee list
        return emp;
    }

    //Finding an employee in file
    public int findInFile(int empNum){
        //Variables for searching algorithm
        List<Employee> emp = getEmp();
        int min = 0;
        int max = emp.size() - 1;

        //If the minimum is smaller than the maximum, look from the middle value to minimum or maximum until finding the correct value
        while (min <= max) {
            int mid = (min + max) / 2;
            if (emp.get(mid).getRegNum() < empNum) {
                min = mid + 1;
            } else if (emp.get(mid).getRegNum() > empNum) {
                max = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    //Rewriting file
    public void fileRewrite(List<Employee> empList){
        try {
            //File writing variables
            FileWriter writer = new FileWriter(fileName, false);
            BufferedWriter buffWriter = new BufferedWriter(writer);
            //Writing the employee list into the file
            for(int i = 0; i < empList.size(); i++){
                buffWriter.write(empList.get(i).getRegNum() + "," + empList.get(i).getJoinDate() + "," +
                        empList.get(i).getName() + "," + empList.get(i).getAddress() + "," + empList.get(i).getPhoneNum() +
                        "," + empList.get(i).getDesignation() + "," + empList.get(i).getGrade() + "," +
                        empList.get(i).getBasePay() + "," + empList.get(i).getTravelAllowance() + ","
                        + empList.get(i).getOvertimeRate() + "," + empList.get(i).getLoan());
                buffWriter.newLine();
            }
            //Closing connection
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //File Sorting
    void sort(List <Employee> empList, RedBlackTree rb)
    {
        //Calculating the minimum and maximum of the list
        int min = 1;
        int max = 1;
        for (int i = 0; i < empList.size(); i++)
        {
            if(empList.get(i).getRegNum() < min){
                min = empList.get(i).getRegNum();
            }
            if(empList.get(i).getRegNum() > max){
                max = empList.get(i).getRegNum();
            }
        }
        int range = max - min + 1;
        int counter[] = new int[range];
        int storage[] = new int[empList.size()];
        //Counting each element and placing it in the correct index
        for (int i = 0; i < empList.size(); i++) {
            counter[empList.get(i).getRegNum() - min]++;
        }
        //Modifying the count by adding the previous counter
        for (int i = 1; i < counter.length; i++) {
            counter[i] += counter[i - 1];
        }
        //Building the output array
        for (int i = empList.size() - 1; i >= 0; i--) {
            storage[counter[empList.get(i).getRegNum() - min] - 1] = empList.get(i).getRegNum();
            counter[empList.get(i).getRegNum() - min]--;
        }
        //Copying the output array
        for (int i = 0; i < empList.size(); i++) {
            Node temp = rb.searchNode(storage[i]);
            empList.set(i, temp.emp);
        }

        fileRewrite(empList);
    }
}
