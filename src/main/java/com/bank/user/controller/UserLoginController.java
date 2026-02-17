package com.bank.user.controller;


import java.sql.SQLException;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.bank.model.User;
import com.bank.service.UserService;

public class UserLoginController extends SelectorComposer<Window> {
	
	@Wire private Textbox mailTB;
	@Wire private Textbox passwordTB;
	@Wire private Button loginBtn;

    @Listen("onClick=#loginBtn")
    public void loginUser()
    {
    	UserService userService=new UserService();
    	String email=mailTB.getValue();
    	String uPassword=passwordTB.getValue();
    
    	try {
			Boolean isValidUser=userService.verifyUser(email,uPassword);
			if (isValidUser) {
			
			    Executions.sendRedirect("/user/userdashboard.zul");
			    
			} else {
				 Clients.showNotification(
					        "Invalid email or password",
					        Clients.NOTIFICATION_TYPE_ERROR,
					        null,
					        "top_center",
					        3000
					    );
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Clients.showNotification("Service Unavailable !!!!!Unable to Login .");
			e.printStackTrace();
		}
    }
    
}
