package com.bank.user.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.bank.dao.TransactionDao;
import com.bank.model.TransactionHistory;
import com.bank.model.User;

public class UserTransactionController extends SelectorComposer<Window> {

    @Wire
    private Datebox fromDate;

    @Wire
    private Datebox toDate;

    @Wire
    private Listbox txnList;

    private final TransactionDao txnDao = new TransactionDao();

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        loadAllTransactions();
    }

    private void loadAllTransactions() {
        try {
            User user = (User) Executions.getCurrent()
                    .getSession()
                    .getAttribute("currentUser");
//
//            if (user == null) {
//                Executions.sendRedirect("/index.zul");
//                return;
//            }

            List<TransactionHistory> list =
                    txnDao.getTransactionsByUser(user.getUserId());

            txnList.setModel(new ListModelList<>(list));

        } catch (SQLException e) {
            Clients.showNotification("Unable to load transactions");
            e.printStackTrace();
        }
    }
        
    @Listen("onClick=#filterBtn")
    public void filterTransactions() {
        try {
            Date from = fromDate.getValue();
            Date to = toDate.getValue();
//
//            if (from == null || to == null) {
//                Clients.showNotification("Please select both dates");
//                return;
//            }

            User user = (User) Executions.getCurrent()
                    .getSession()
                    .getAttribute("currentUser");

            List<TransactionHistory> list =
                    txnDao.getTransactionsByUserAndDate(
                            user.getUserId(), from, to);

            txnList.setModel(new ListModelList<>(list));

        } catch (SQLException e) { 
            Clients.showNotification("Error while filtering transactions");
            e.printStackTrace();
        }
    }
}
