//Employee operations class
import java.io.File;
import java.util.List;
import javax.swing.*;

public class EmployeeOperations {
    //Variables needed for employee operations
    private int count;
    private RedBlackTree rbTree;

    //Creating the employee operator
    public EmployeeOperations(){
        //Reading the file to get the size that the tree should be at
        File payrollFile = new File("Payroll File.txt");
        if(payrollFile.length() != 0) {
            List<Employee> employeeList;
            employeeList = Payroll.txtFile.getEmp();
            count = employeeList.size();
        }
        else{
            count = 0;
        }
        //Creating teh red-black tree
        rbTree = new RedBlackTree(count);
    }

    //Adding employee to text file, red-black tree, and database
    public void addEmployee(int regNum, String date, String name, String address, int phoneNum, String designation,
                            String grade, double basePay, double travelAllowance, double overtimeRate, double loan){
        //Creating an employee based on the information give
        Employee employee = new Employee(regNum, date, name, address, phoneNum, designation, grade, basePay, travelAllowance,
                overtimeRate, loan);
        //Add employee to red-black tree
        rbTree.insertNode(employee);
        //Add employee to text file and sort the file
        Payroll.txtFile.add(employee);
        List<Employee> employeeList = Payroll.txtFile.getEmp();
        Payroll.txtFile.sort(employeeList, rbTree);
        //Add employee to database
        Payroll.db.addLoginInfo(employee);
        count += 1;
        System.out.println("An employee has been added to the system");

    }
    //Print employee pay stub
    public void printEmployeePayStub(int regNum){
        //If the employee is not found let the user know
        Employee employee = findEmployee(regNum);
        if(employee != null) {
            //Get month and overtime hours
            int overTime = Payroll.getOverTimeHours();
            int month = Payroll.getMonth();
            //Calculate the employee's pay stub
            employee.calcPayStub(month, overTime);
        }
        else{
            System.out.println("The employee does not exist");
        }
    }

    //Print all employees
    public void printEmployees(){
        //Variables for the table that holds the employee information
        List<Employee> employeeList = Payroll.txtFile.getEmp();
        double basePay;
        double houseAllowance;
        double healthAllowance;
        JFrame frame=new JFrame();
        String[] columnNames = {"Name", "Registration Number", "Join Date", "Address", "Phone Number", "Designation",
            "Grade", "Base Salary", "Travel Allowance", "Health Allowance", "House Allowance", "Overtime Rate", "Loan"};
        String[][] data = new String[employeeList.size()][13];
        //Storing all the employee information
        for(int i = 0; i < employeeList.size(); i ++){
            basePay = employeeList.get(i).getBasePay();
            healthAllowance  = Math.round(basePay * 0.05 * 100.00)/ 100.00;
            houseAllowance  = Math.round(basePay * 0.8 * 100.00)/ 100.00;
            data[i][0] = employeeList.get(i).getName();
            data[i][1] = Integer.toString(employeeList.get(i).getRegNum());
            data[i][2] = String.valueOf(employeeList.get(i).getJoinDate());
            data[i][3] = employeeList.get(i).getAddress();
            data[i][4] = Integer.toString(employeeList.get(i).getPhoneNum());
            data[i][5] = employeeList.get(i).getDesignation();
            data[i][6] = employeeList.get(i).getGrade();
            data[i][7] = Double.toString(employeeList.get(i).getBasePay());
            data[i][8] = Double.toString(employeeList.get(i).getTravelAllowance());
            data[i][9] = Double.toString(healthAllowance);
            data[i][10] = Double.toString(houseAllowance);
            data[i][11] = Double.toString(employeeList.get(i).getOvertimeRate());
            data[i][12] = Double.toString(employeeList.get(i).getLoan());
        }
        //Table storing the employees information
        JTable table=new JTable(data, columnNames);
        table.setBounds(30,40,200,300);
        JScrollPane scroll=new JScrollPane(table);
        frame.add(scroll);
        frame.setSize(600,300);
        frame.setVisible(true);
    }

    //Finding employee
    public Employee findEmployee(int regNum){
        //Searching through the red-black tree to see if an employee exists
        Node searchedNum;
        try {
            searchedNum= rbTree.searchNode(regNum);
            return searchedNum.emp;
        }
        catch (Exception e){
            return null;
        }
    }

    //Modifying an employee
    public void modifyEmployee(int regNum){
        //Finding the employee to modify
        Employee deletedEmployee = findEmployee(regNum);
        //Deleting and adding the modified employee to the red-black tree
        int valid = rbTree.deleteNode(regNum);
        if (valid == 1) {
            //Modifying the text file
            Employee modifiedEmployee = Payroll.txtFile.modify(regNum);
            rbTree.insertNode(modifiedEmployee);
            //Rewriting and sorting the file
            int editLoc = Payroll.txtFile.findInFile(regNum);
            List<Employee> emp = Payroll.txtFile.getEmp();
            emp.set(editLoc, modifiedEmployee);
            Payroll.txtFile.fileRewrite(emp);
            Payroll.txtFile.sort(emp, rbTree);
            //Modifying the database information for the employee
            Payroll.db.deleteLoginInfo(deletedEmployee);
            Payroll.db.addLoginInfo(modifiedEmployee);
            System.out.println("The employee has been modified");
        }
        else{
            System.out.println("The employee does not exist");
        }

    }
    //Deleting employee
    public void deleteEmployee(int regNum){
        //Finding all the employees
        List<Employee> empList = Payroll.txtFile.getEmp();
        //Looping through the list to delete the employee login information
        for(int i = 0; i < empList.size(); i++)
        {
            if(empList.get(i).getRegNum() == regNum){
                Payroll.db.deleteLoginInfo(empList.get(i));
            }
        }
        //Deleting the employee from the text file and red-black tree
        if (count > 0){
            Payroll.txtFile.delete(regNum);
            rbTree.deleteNode(regNum);
            count -= 1;
        }
        System.out.println("The selected employee has been deleted if they were in the system");

    }


}
