package com.bank.user.controller;

import java.math.BigDecimal;
import java.net.Authenticator.RequestorType;
import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.bank.dao.UserDao;
import com.bank.model.Request;
import com.bank.service.UserService;

public class MakeTransactionComposer  extends SelectorComposer<Window>{
	
	@Wire Button submitBtn;
	@Wire Combobox requestTypeBox;
	@Wire Textbox amountBox;
	

	
	UserDao dao=new UserDao();
	UserService userService = new UserService();
	
	
	@Listen("onClick=#submitBtn")
	 public void submitTxsRequest() throws SQLException
	 {
		if (requestTypeBox.getSelectedItem() == null) {
		    Clients.showNotification(
		        "Please select transaction type",
		        Clients.NOTIFICATION_TYPE_ERROR,
		        null,
		        "top_center",
		        3000
		    );
		    return;
		}
     
		if (amountBox.getValue() == null || amountBox.getValue().isEmpty()) {
		    Clients.showNotification(
		        "Please enter amount",
		        Clients.NOTIFICATION_TYPE_ERROR,
		        null,
		        "top_center",
		        3000
		    );
		    return;
		}


		BigDecimal amount = new BigDecimal(amountBox.getValue());
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
		    Clients.showNotification(
		        "Amount must be greater than 0",
		        Clients.NOTIFICATION_TYPE_ERROR,
		        null,
		        "top_center",
		        3000
		    );
		    return;
		}
       
     // System.out.println("clicked submit button");
	  Request.RequestType reqType= Request.RequestType.valueOf(requestTypeBox.getSelectedItem().getLabel());
			 
	  String amt=amountBox.getValue();
	 // BigDecimal amount=new BigDecimal(amt);
	  userService.handleTransactionRequest (reqType , amount);   
	   
	 }

//	public static void main(String[] args) {
//		System.out.println("1");
//		MakeTransactionComposer ob=new MakeTransactionComposer();
//		System.out.println("2");
//		try {
//			System.out.println("3");
//
//			ob.submitTxsRequest();
//			System.out.println("4");
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
