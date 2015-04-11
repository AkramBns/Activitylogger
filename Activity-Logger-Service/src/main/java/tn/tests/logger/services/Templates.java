package tn.tests.logger.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import tn.tests.logger.model.Category;
import tn.tests.logger.model.ElementProperty;
import tn.tests.logger.model.Model;

/**
 * Servlet implementation class Templates
 */
public class Templates extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private VelocityEngine ve;
	
	private Template formTemplate;
	
	private VelocityContext context ;
	
	private Model model;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Templates() {
        super();
        
        model = new Model();
        ve = new VelocityEngine();

        // initialize velocity engine	
        ve.init();        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String catName = request.getParameter("category");
		
		if(catName == null) {
			return;
		}
		
		 
		Category Category = model.getCategory(catName);
		ArrayList<ElementProperty> list = new ArrayList<ElementProperty>();
		Iterator<String> headersMappingItr = Category.getHeaderMapping().keySet().iterator();
		while (headersMappingItr.hasNext()) {
			String string = (String) headersMappingItr.next();
			//skip the details field as it is already represented by a textarea
			//in the template that will be returned
			if(!string.equalsIgnoreCase(Model.Details_Column_Caption)) {
				list.add(new ElementProperty(Category.getHeaderMapping().get(string),string));
			}
		}
		
		
		
		request.setAttribute("gridHeaders", list);		
		RequestDispatcher requestDispatcher =  request.getRequestDispatcher("formTemplate.vm");
        requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
