package com.bank.employee.controller;




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

import com.bank.service.EmployeeService;
import com.bank.service.UserService;

public class EmployeeLoginPageController extends SelectorComposer<Window> {
	
	@Wire private Textbox mailTB;
	@Wire private Textbox passwordTB;
	@Wire private Button loginBtn;

    @Listen("onClick=#loginBtn")
    public void loginUser()
    {
    	EmployeeService  employeService=new EmployeeService();
    	String email=mailTB.getValue();
    	String uPassword=passwordTB.getValue();
    	
    	try {
			Boolean isValidEmployee=employeService.verifyEmployee(email,uPassword);
			if (isValidEmployee) {
				
			    Executions.sendRedirect("/employee/EmployeeDashBoard.zul");
			} else {
			    Clients.showNotification("Invalid email or password", "error", loginBtn, "after_start", 3000);
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Clients.showNotification("Service Unavailable !!!!!Unable to Login .");
			e.printStackTrace();
		}
    }
    
}
