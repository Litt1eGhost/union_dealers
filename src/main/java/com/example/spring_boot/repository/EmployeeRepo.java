package com.example.spring_boot.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;

import com.example.spring_boot.config.DatabaseConnector;
import com.example.spring_boot.entity.Employee;
import com.example.spring_boot.entity.UserDetails;
public class EmployeeRepo{

    public static void add_new_emp(long emp_id) {
        String sql = "INSERT INTO employee (emp_id) VALUES (?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, emp_id);

            pstmt.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update customer details
    public static void upd_emp_detail(Long emp_id, String column, String newValue) {
        String sql = "UPDATE employee SET " + column + " = ? WHERE emp_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newValue);
            pstmt.setLong(2, emp_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee details updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Overloaded method to update numeric fields like phone number, AadharNo
    public static void upd_emp_detail(Long emp_id, String column, BigDecimal newValue) {
        String sql = "UPDATE employee SET " + column + " = ? WHERE emp_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, newValue);
            pstmt.setLong(2, emp_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee details updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    public static void upd_emp_detail(Long emp_id, String column, Integer newValue) {
        String sql = "UPDATE employee SET " + column + " = ? WHERE emp_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newValue);
            pstmt.setLong(2, emp_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee details updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to query an employee by ID and return as a Map
    public static Employee getCustomerById(Long custId) {
        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        Employee customerData = null; // Initialize as null

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, custId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customerData = new Employee(); // Initialize customerData here
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    // Fixing variable name from userDetails to customerData
                    switch (columnName) {
                        case "emp_id": // Changed to match database column name
                            customerData.setEmpId((Long) columnValue);
                            break;
                        case "salary":
                            customerData.setSalary((Integer) columnValue);
                            break;
                        case "phoneNo":
                            customerData.setPhoneNo((BigDecimal) columnValue);
                            break;
                        case "dept_id":
                            customerData.setDept((Long) columnValue);
                            break;
                        case "first_name":
                            customerData.setFirstName((String) columnValue);
                            break;
                        case "middle_name":
                            customerData.setMiddleName((String) columnValue);
                            break;
                        case "last_name":
                            customerData.setLastName((String) columnValue);
                            break;
                        case "houseNo":
                            customerData.setHouseNo((String) columnValue);
                            break;
                        case "street":
                            customerData.setStreet((String) columnValue);
                            break;
                        case "city":
                            customerData.setCity((String) columnValue);
                            break;
                        case "state":
                            customerData.setState((String) columnValue);
                            break;
                        case "AadharNo":
                            customerData.setAadharNo((BigDecimal) columnValue);
                            break;
                        default:
                            break; // or handle unexpected columns
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerData; // This could return null if not found
    }
}