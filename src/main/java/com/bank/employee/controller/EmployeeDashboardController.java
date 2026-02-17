package com.bank.employee.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class EmployeeDashboardController extends SelectorComposer<Window> {
	@Wire Center centerUserDsh;
	@Wire Div makeTxs,txsHistory,pendingTxsDiv ,centerDiv;
	@Wire Include contentInclude1;
	@Wire Label centerLebel;
	//@Listen("onClick=#pendingTxsDiv")
	@Wire Button logutBtn;
	
	@Listen("onClick=#pendingTxsDiv")
	public void makeTransaction()
	{    
		centerDiv.setVisible(false);
		contentInclude1.setSrc("/employee/employeePendingTransactionPage.zul");	
	}
	
	@Listen("onClick=#logutBtn")
	public void logout() {
	    Sessions.getCurrent().invalidate();
	    Executions.sendRedirect("/employee/employeeLoginPage.zul");
	}

	
}





