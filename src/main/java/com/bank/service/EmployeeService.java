package com.bank.service;

import java.sql.SQLException;

import org.zkoss.zk.ui.Executions;

import com.bank.dao.EmployeeDao;
import com.bank.dao.UserDao;
import com.bank.model.Employee;
import com.bank.model.User;

public class EmployeeService {

		public Boolean verifyEmployee(String email, String uPassword) throws SQLException {
		// TODO Auto-generated method stub
	    EmployeeDao empDao=new EmployeeDao();		
		Employee employee=empDao.getEmployeeByMail(email);
		if(employee!=null && employee.getPassword().equals(uPassword))
		{	
			Executions.getCurrent().getSession().setAttribute("currentEmployee", employee);
			 return true;
		}
		return false;
	}

	
}
