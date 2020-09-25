package com.google.datastore;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.SortDirection;

@WebServlet(
    name = "GoogleDatastoreServlet",
    urlPatterns = {"/employee"}
)
public class GoogleDatastoreServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
   
    PrintWriter out = response.getWriter();
    
    DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    
    Entity employee = new Entity("Employee");
    employee.setProperty("emailId", "nitin@email.com");
    employee.setProperty("name", "Nitin");
	employee.setProperty("salary", 7000);
	employee.setProperty("Designation", "SMM");
	
	datastoreService.put(employee);
	
	Entity employee1 = new Entity("Employee");
	employee1.setProperty("emailId", "rohit@email.com");
	employee1.setProperty("name", "Mohit");
	employee1.setProperty("salary", 9000);
	employee1.setProperty("Designation", "Operator");
	
	datastoreService.put(employee1);
	
	Entity employee2 = new Entity("Employee");
    employee2.setProperty("emailId", "mohit@email.com");
    employee2.setProperty("name", "Mohit");
	employee2.setProperty("salary", 11000);
	employee2.setProperty("Designation", "Database Management");
	
	datastoreService.put(employee2);
	
	
	
	Query query = new Query("Employee");
	
	//Filter propertyFilter = new Query.FilterPredicate("salary", Query.FilterOperator.GREATER_THAN_OR_EQUAL, 8000);
	
	//query.setFilter(propertyFilter);
	//query.addSort("salary");
	
	query.addSort("name", SortDirection.ASCENDING);
	query.addSort("salary", SortDirection.DESCENDING);
	
	PreparedQuery preparedQuery = datastoreService.prepare(query);
	
	out.println("<p><strong> The Employee Information is: </strong> <br>");
	int i=1;
	for(Entity entity : preparedQuery.asIterable()) {
		
		out.println("<br>Employee : "+i+ " Information<br>");
		
		String email = (String)entity.getProperty("emailId");
		String name = (String)entity.getProperty("name");
		long salary = (long)entity.getProperty("salary");
		String designation = (String)entity.getProperty("Designation");
		
		out.println("Email Id: "+email+" <br> Employee Name : "+name+" <br>Employee Salary: "+salary+" <br>Designation: "+designation);
		out.println("<br>");
		i=i+1;
	}
  }
}