package com.bank.service;

import java.io.ObjectInputFilter.Status;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.spi.TransactionalWriter;

import org.zkoss.zk.ui.Executions;

import com.bank.dao.TransactionDao;
import com.bank.dao.UserDao;
import com.bank.model.ApprovalRule;
import com.bank.model.Request;
import com.bank.model.Request.RequestType;
import com.bank.model.User;

public class UserService {
	UserDao dao=new UserDao();
	public boolean verifyUser(String email, String uPassword) throws SQLException
	{
		System.out.println("verufy user");
		User user=dao.getUserByMail(email);
		if(user!=null && user.getPassword().equals(uPassword))
		{	
			Executions.getCurrent().getSession().setAttribute("currentUser", user);
			
	        return true;
		}
		return false;
	}
	
	public String getApprovalLevel(BigDecimal amount)
	{
    	try {
    		
    		List<ApprovalRule> approvalRuleList=dao.getAllApprovalRule();
    	      for (ApprovalRule rule : approvalRuleList) {

    	            int compare = amount.compareTo(rule.getFieldValue());

    	            switch (rule.getOperator()) {

    	                case "<=":
    	                    if (compare <= 0) return rule.getApprovalLevel();
    	                    break;

    	                case ">":
    	                    if (compare > 0) return rule.getApprovalLevel();
    	                    break;

    	                case "<":
    	                    if (compare < 0) return rule.getApprovalLevel();
    	                    break;

    	                case ">=":
    	                    if (compare >= 0) return rule.getApprovalLevel();
    	                    break;

    	                case "=":
    	                    if (compare == 0) return rule.getApprovalLevel();
    	                    break;
    	            }
    	        }
    	        return "MANUAL"; // default fallback
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		return null;
	}
	
	public void handleTransactionRequest(RequestType reqType, BigDecimal amount) throws SQLException
	{   UserDao userDao=new UserDao();
		UserService userService = new UserService();
		User user=(User) Executions.getCurrent().getSession().getAttribute("currentUser");

		 Long user_id=user.getUserId();                          
		 Long account_number=user.getAccountNumber();          
		   String approval_level = userService.getApprovalLevel(amount);
		   
		   Request request=new Request();
		   request.setAmount(amount);
		   request.setUserId(user_id);
		   request.setRequestType(reqType);
		   request.setApprovalLevel(approval_level);
		   request.setAccountNumber(account_number);
		   BigDecimal autoLimit=userDao.getAutoApprovalLimit();
		//   BigDecimal autoLimit=new BigDecimal(10000);
		   
		   if(amount.compareTo(autoLimit)<=0)
		   {
			  TransactionDao daoTxs=new TransactionDao();
			  daoTxs.doTransaction(user.getUserId(),reqType,amount);
			  request.setStatus(Request.Status.APPROVED);
			 }
		   else
		   {
			   request.setStatus(Request.Status.PENDING);   
		   }
		   
		   dao.submitTransactionRequest(request);
	}
	
//	public static void main(String[] args) {
//		UserService oo=new UserService();
//		System.out.println("Sdvfsd");
//		//oo.getApprovalLevel(new BigDecimal("10000"));
//		System.out.println(oo.getApprovalLevel(new BigDecimal("55555")));
//	}
}
