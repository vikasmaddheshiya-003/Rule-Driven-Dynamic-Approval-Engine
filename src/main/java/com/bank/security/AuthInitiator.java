package com.bank.security;

import java.util.Map;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import com.bank.model.User;
import com.bank.model.Employee;
import com.bank.model.Employee.Role;

public class AuthInitiator implements Initiator {

    @Override
    public void doInit(Page page, Map<String, Object> args) throws Exception {

        Execution exec = page.getDesktop().getExecution();
        String uri = page.getRequestPath();
           System.out.println(".....................////////////...........////////////");
        User currentUser =
                (User) exec.getSession().getAttribute("currentUser");

        Employee currentEmployee =
                (Employee) exec.getSession().getAttribute("currentEmployee");

        /* -------- PUBLIC PAGES -------- */
        if (uri.endsWith("index.zul")
                || uri.endsWith("userLoginPage.zul")
                || uri.endsWith("employeeLoginPage.zul")) {
            return;
        }

        /* -------- USER PAGES -------- */
        if (uri.startsWith("/user/")) {
            if (currentUser == null) {
                exec.sendRedirect("/userLoginPage.zul");
            }
            return;
        }

        /* -------- EMPLOYEE PAGES -------- */
        if (uri.startsWith("/employee/")) {

            if (currentEmployee == null) {
                exec.sendRedirect("/employee/employeeLoginPage.zul");
                return;
            }

            // Role rule for Employee Dashboard
//            if (uri.endsWith("EmployeeDashBoard.zul")) {
//                Role role = currentEmployee.getRole();
//                if (role != Role.MANAGER && role != Role.DIRECTOR) {
//                    exec.sendRedirect("/accessDenied.zul");
//                }
//            }
        }
    }
}
