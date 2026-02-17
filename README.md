# Rule-Driven-Dynamic-Approval-Engine
A Java-based dynamic approval engine that processes requests using configurable business rules stored in a MySQL database. The engine dynamically loads and evaluates rules at runtime, eliminating hardcoded logic and enabling flexible, scalable, and real-time multi-level approval workflows.
 Tech Stack:-
Java
MySQL
JDBC
Maven

How It Works:-
A request is submitted with an amount value.
The engine fetches all rules from the approval_rule table.
Rules are evaluated dynamically based on:
field_name
operator
field_value

Based on the matched rule, the request is routed to the appropriate approval level:
AUTO
MANAGER
DIRECTOR

No approval thresholds are hardcoded in the application.

 Database Schema:-
approval_rule Table
SELECT * FROM approval_rule;

rule_id	   field_name   	operator  	field_value	  approval_level
1	            amount      	<=	       15000	        AUTO
2	            amount      	<=	       40000	        MANAGER
3	            amount	       >	       40000	        DIRECTOR

Rule Logic Explanation
If amount <= 15000 → Auto Approved
If amount <= 40000 → Manager Approval Required
If amount > 40000 → Director Approval Required

Rules are evaluated dynamically at runtime, allowing business teams to modify approval logic directly in the database without changing application code.
