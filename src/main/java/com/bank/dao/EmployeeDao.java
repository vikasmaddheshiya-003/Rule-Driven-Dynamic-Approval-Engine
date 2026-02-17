package com.bank.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.bank.db.DBConnection;
import com.bank.model.Employee;
import com.bank.model.Request;



public class EmployeeDao {
	public  List<Request> getPendingTxsByApprovalLevel() throws SQLException {

	    List<Request> pendingTxs = new ArrayList<>();

	    Employee emp=(Employee) Executions.getCurrent().getSession().getAttribute("currentEmployee");
	    String approvalLevel =emp.getRole().name();

	    String sql = "SELECT request_id, user_id, request_type, amount, status, " +
	                 "approval_level, created_at, account_number " +
	                 "FROM request " +
	                 "WHERE approval_level = ? AND status = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, approvalLevel);
	        pst.setString(2, "PENDING");

	        try (ResultSet rs = pst.executeQuery()) {

	            while (rs.next()) {

	                Request req = new Request();

	                req.setRequestId(rs.getLong("request_id"));
	                req.setUserId(rs.getLong("user_id"));
	                req.setAccountNumber(rs.getLong("account_number"));
	                req.setRequestType(
	                        Request.RequestType.valueOf(rs.getString("request_type"))
	                );
	                req.setAmount(rs.getBigDecimal("amount"));
	                req.setStatus(
	                        Request.Status.valueOf(rs.getString("status"))
	                );
	                req.setApprovalLevel(rs.getString("approval_level"));
	                req.setCreatedAt(rs.getTimestamp("created_at"));

	                pendingTxs.add(req);
	            }
	        }
	    }

	    return pendingTxs;
	}

	public Employee getEmployeeByMail(String email) throws SQLException {

	    Employee employee = null;

	    String sql = "SELECT * FROM employee WHERE gmail = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, email);

	        try (ResultSet rs = pst.executeQuery()) {

	            if (rs.next()) {

	                Long empId = rs.getLong("emp_id");
	                String name = rs.getString("name");
	                String gmail = rs.getString("gmail");
	                String password = rs.getString("password");

	                Employee.Role role =
	                        Employee.Role.valueOf(rs.getString("role"));

	                employee = new Employee( empId,name, gmail, password, role );
	            }
	        }
	    }
	    return employee;
	}

	public int approveCardRequests(Request req) throws SQLException {
		// TODO Auto-generated method stub
		int n;
	   String sql="Update request set status=? where request_id=?";
	   
	   try(Connection con=DBConnection.getConnection();
		   PreparedStatement pst=con.prepareStatement(sql);	   
			   )
	   {
		  pst.setString(1,"APPROVED");
		  pst.setLong(2, req.getRequestId());
		  n=pst.executeUpdate();
	   }
	   return n;
	    
	}

	public int rejectTraxRequest(Request request) throws SQLException {
		// TODO Auto-generated method stub
		
		int n;
		String sql="Update request set status=? where request_id=?";
		   
		   try(Connection con=DBConnection.getConnection();
			   PreparedStatement pst=con.prepareStatement(sql);	   
				   )
		   {
			  pst.setString(1,"REJECTED");
			  pst.setLong(2, request.getRequestId());
			   n=pst.executeUpdate();
		   }
		  return n;
	}
	
}
