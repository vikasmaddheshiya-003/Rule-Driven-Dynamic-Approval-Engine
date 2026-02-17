package com.bank.security;

import java.util.Map;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Initiator;

import com.bank.model.Employee;

public class EmployeeAuthInitiator implements Initiator {

    @Override
    public void doInit(Execution exec, Map<String, Object> args) throws Exception {

        Session session = exec.getSession();
        Employee emp = (Employee) session.getAttribute("currentEmployee");

        if (emp == null) {
            Executions.sendRedirect("/employee/employeeLoginPage.zul");
            return;
        }

        // optional role check
        if (emp.getRole() == null) {
            Executions.sendRedirect("/index.zul");
        }
    }
}
