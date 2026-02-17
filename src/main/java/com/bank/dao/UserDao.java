package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import com.bank.db.DBConnection;
import com.bank.model.ApprovalRule;
import com.bank.model.Request;
import com.bank.model.User;

public class UserDao {

    public User getUserByMail(String email) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Long userId = rs.getLong("user_id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                BigDecimal balance = rs.getBigDecimal("balance");
                String dbEmail = rs.getString("email");
                
                User.UserStatus status;
                try {
                    status = User.UserStatus.valueOf(rs.getString("status"));
                } catch (Exception e) {
                    status = User.UserStatus.INACTIVE;
                }
                Long accountNumber=rs.getLong("account_number");
                Timestamp createdAt = rs.getTimestamp("created_at");

                user = new User(userId, name, password, balance, dbEmail,accountNumber, status, createdAt);
            }
        }
      return user;
    }
    
    public List<ApprovalRule> getAllApprovalRule() throws SQLException
    {
    	List<ApprovalRule> allApprovalRules=new ArrayList<>();
    	String sql="select * from approval_rule";
    	try(Connection con=DBConnection.getConnection();
    			PreparedStatement pst=con.prepareStatement(sql);
    			)
    	{
    	  ResultSet rs=pst.executeQuery();
    	  while(rs.next())
    	  {
    		  ApprovalRule aplRule=new ApprovalRule();
    		  aplRule.setRuleId(rs.getLong("rule_id"));
    		  aplRule.setFieldName(rs.getString("field_name"));
    		  aplRule.setOperator(rs.getString("operator"));
    		  aplRule.setFieldValue(new BigDecimal(rs.getString("field_value")));
    		  aplRule.setApprovalLevel(rs.getString("approval_level"));
    		  
    		  allApprovalRules.add(aplRule);
    	  }
    	  
    	}
    	
		return allApprovalRules;
    	
    }
    
    public void  submitTransactionRequest(Request request) throws SQLException
    {
    	String sql="Insert into request (user_id,request_type,amount,approval_level,account_number,status) values (?,?,?,?,?,?)";
      try(Connection con=DBConnection.getConnection();
    		PreparedStatement pst=con.prepareStatement(sql)  
    		  )
      {
    	  
    	 pst.setLong(1,request.getUserId() );
    	 pst.setString(2, request.getRequestType().name());
    	 pst.setBigDecimal(3, request.getAmount());
    	 pst.setString(4, request.getApprovalLevel());
    	 pst.setLong(5, request.getAccountNumber());
    	 pst.setString(6,request.getStatus().name());
    	 int n=pst.executeUpdate();
    	 if(n==1)
    	 {
    		 Clients.showNotification(
    				    "Transaction request submitted successfully",
    				    Clients.NOTIFICATION_TYPE_INFO,
    				    null,
    				    "top_center",
    				    3000
    				);
    	 }
    	 else {
    		 Clients.showNotification(
 				    "Unable to Submit Request ",
 				    Clients.NOTIFICATION_TYPE_ERROR,
 				    null,
 				    "top_center",
 				    3000
 				);
    	 }
    	 
    }
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
	
	public  List<Request> getUserPendingTxn() throws SQLException {

	    List<Request> pendingTxs = new ArrayList<>();

	     String sql = "SELECT * FROM request WHERE status = ? AND user_id = ?";
		 User user=(User) Executions.getCurrent().getSession().getAttribute("currentUser");
         System.out.println(".........."+user);
         Long userId=user.getUserId();  
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	     
	        pst.setString(1, "PENDING");
            pst.setLong(2, userId);            
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
	
	public BigDecimal getCurrentUserBalance() throws SQLException
	{
		User user=(User) Executions.getCurrent().getSession().getAttribute("currentUser");
		String sql="select balance from users where user_id=? ";
		try(Connection con=DBConnection.getConnection();
		    PreparedStatement pst=con.prepareStatement(sql)		
				)
		{
		   pst.setLong(1, user.getUserId());
		   ResultSet rs=pst.executeQuery();
		   rs.next();
		   return rs.getBigDecimal("balance");
		}
		
		
	}
	
	public BigDecimal getAutoApprovalLimit() throws SQLException {

		   String sql = "SELECT field_value FROM approval_rule WHERE approval_level = 'AUTO' LIMIT 1";

		   try (Connection con = DBConnection.getConnection();
		        PreparedStatement ps = con.prepareStatement(sql);
		        ResultSet rs = ps.executeQuery()) {

		       if (rs.next()) {
		           return rs.getBigDecimal("field_value");
		       }
		   }

		   return BigDecimal.ZERO; // fallback safety
		}
    public static void main(String[] args) {
	   UserDao ob=new UserDao();
	   try {
		   System.out.println(ob.getCurrentUserBalance());
		ob.getCurrentUserBalance();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
