//Database class
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;

public class Database {
    //Variables for database
    private Connection conn;
    private String url;
    private String path;
    private String employeeEmail;
    private String employeePassword;

    //Creating a database
    public Database()
    {
        //If the database exists, connect to it
        try
        {
            path = new File(".").getCanonicalPath();
            url = "jdbc:sqlite:" + path + "\\sqliteDB\\DASA.db";
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException | IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            //Close the database connection
            try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    //Adding an employee login into the database
    public void addLoginInfo(Employee employee){
        //Connecting to the database
        try
        {
            conn = DriverManager.getConnection(url);
            employeeEmail = employee.getName() + "@company.org";
            employeePassword = employee.getRegNum() + employee.getName() + employee.getPhoneNum();
            //Adding the email and password into the database
            try
            {
                PreparedStatement sqlCommand = conn.prepareStatement("INSERT INTO Login_tbl " +
                        "(username, password) VALUES (?, ?);");
                sqlCommand.setString(1, employeeEmail);
                sqlCommand.setString(2, employeePassword);
                sqlCommand.executeUpdate();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                //Closing the database connection
                try
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Deleting the employee's login information
    public void deleteLoginInfo(Employee employee){
        //Connecting to database
        try
        {
            conn = DriverManager.getConnection(url);
            employeeEmail = employee.getName() + "@company.org";
            employeePassword = employee.getRegNum() + employee.getName() + employee.getPhoneNum();
            //Deleting the employee email and password from the database
            try
            {
                PreparedStatement sqlCommand = conn.prepareStatement("DELETE FROM Login_tbl WHERE " +
                        "username = ? and password = ?;");
                sqlCommand.setString(1, employeeEmail);
                sqlCommand.setString(2, employeePassword);
                sqlCommand.executeUpdate();

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                //Closing the database connection
                try
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Verifying log in information
    public Boolean verifyLoginInfo(String employeeEmail, String employeePassword){
        //Connecting to database
        Boolean verify = false;
        try
        {
            conn = DriverManager.getConnection(url);
            //checking if the employee is in the database
            try
            {
                PreparedStatement sqlCommand = conn.prepareStatement("SELECT * FROM Login_tbl WHERE " +
                        "username = ? and password = ?;");
                sqlCommand.setString(1, employeeEmail);
                sqlCommand.setString(2, employeePassword);
                ResultSet result = sqlCommand.executeQuery();

                //If the user exists in teh database return true
                if (result.next() == true)
                {
                    verify = true;
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                //Closing the database connection
                try
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        //Returning if the employee exists
        return verify;
    }

}
