package com.bank.user.controller;



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



public class UserDashBoardController extends SelectorComposer<Window> {
	@Wire Button logutBtn;
	@Wire Center centerUserDsh;
	@Wire Div makeTxs,txsHistory,pendingTxs;
	@Wire Include contentInclude;
	@Wire Label centerLebel;
	
	@Listen("onClick=#makeTxs")
	public void makeTransaction()
	{    
		centerLebel.setVisible(false);
		contentInclude.setSrc("/user/makeTransaction.zul");	
	}
	
	@Listen("onClick=#pendingTxs")
	public void showPendingTxs()
	{    
		centerLebel.setVisible(false);
		contentInclude.setSrc("/user/userPendingTransactionPage.zul");	
	}
	
	@Listen("onClick=#logutBtn")
	public void logout() {
	    Sessions.getCurrent().invalidate();
	    Executions.sendRedirect("/user/userLoginPage.zul");
	}

	@Listen("onClick=#txsHistory")
	public void showTransaction() {
		centerLebel.setVisible(false);
		contentInclude.setSrc("/user/transactionHistoryPage.zul");	
	 
	}
	
	
}
