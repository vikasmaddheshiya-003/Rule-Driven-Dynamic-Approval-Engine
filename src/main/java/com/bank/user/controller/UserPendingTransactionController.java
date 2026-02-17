//package com.bank.admin.controller;
//
//
//import java.sql.SQLException;
//import java.util.List;
//
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zk.ui.util.Clients;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.ListModelList;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Window;
//
//import com.bank.dao.EmployeeDao;
//import com.bank.dao.UserDao;
//import com.bank.db.DBConnection;
//import com.bank.model.Request;
//import com.bank.model.Request.RequestType;
//import com.mysql.cj.xdevapi.Client;
//
//
//public class PendingTransactionController extends SelectorComposer<Window>{
//	
//	@Wire Listbox requestList;
//	@Wire Button approveBtn,rejectBtn;
//    @Override
//    public void doAfterCompose(Window comp) throws Exception {
//    	super.doAfterCompose(comp);
//    	loadUserTransaction();
//    }
//    
//   // static PendingTransactionController pendingTrxOb=new PendingTransactionController();
//    
//	private  void loadUserTransaction() throws SQLException {
//		EmployeeDao empDao=new EmployeeDao();
//		List<Request> pendingRequests=empDao.getPendingTxsByApprovalLevel();
//	    requestList.setModel(new ListModelList<>(pendingRequests));
//	}
//
//	@Listen("onClick = #approveBtn")
//	public void approveCardRequest() throws Exception {
//		if (requestList.getSelectedItem() == null) {
//			//NotificationUtil.showInstant("warning", "Please select one Request  first!");
//			Clients.showNotification( "Please select one Request  first!");
//			return;
//		}
//
//		Request req = requestList.getSelectedItem().getValue();
//        
//		UserDao ob = new UserDao();
//		//System.out.println(req);
//		//req.setReviewedBy(adminId);
//		ob.approveCardRequests(req);
//    	loadUserTransaction();
//		Clients.showNotification("Request Transaction approved successfully!");
//
//	}
//
////	@Listen("onClick = #rejectBtn")
////	public void rejectRequest() throws Exception {
////		if (requestList.getSelectedItem() == null) {
////			Messagebox.show("Please select one account first!");
////			return;
////		}
////		Request req = requestList.getSelectedItem().getValue();
////		req.setReviewedBy(adminId); 
////		CardsDAOImpl ob = new CardsDAOImpl();
////		ob.rejectCardRequests(req);
////
////		NotificationUtil.showInstant("info", "Request rejected!");
////		loadPendingCardRequests();
////	}
//
//}

package com.bank.user.controller;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.bank.dao.EmployeeDao;
import com.bank.dao.UserDao;
import com.bank.model.Request;

public class UserPendingTransactionController extends SelectorComposer<Window> {

    @Wire
    private Listbox requestList;

    @Wire
    private Button approveBtn, rejectBtn;

    UserDao userDao = new UserDao();
    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        loadUserTransaction();
    }
   
    private void loadUserTransaction() throws SQLException {
       
        List<Request> pendingRequests = userDao.getUserPendingTxn();
        requestList.setModel(new ListModelList<>(pendingRequests));
    }
  
}




