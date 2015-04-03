package tn.tests.logger.services;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import tn.tests.logger.model.Category;
import tn.tests.logger.model.Model;


@Path("Logs")
public class ItemsService
{
 
	Logger loger = Logger.getLogger(ItemsService.class);
	
	private static Model model = new Model();

    
    @GET
    @Path("category/add")
    public void getCategoryElements(@QueryParam("name") final String name, @QueryParam("properties") final String properties) {
    	
    	Category category = model.getCategory(name);
    	if(category != null && properties != null) {
    		model.parseElement(category,properties);	
    	}    	
    }

    @GET
    @Path("category/edit")
    public void editCategoryElements(@QueryParam("category") final String name, @QueryParam("id") final String id
    		, @QueryParam("properties") final String properties) {
    	
    	Category category = model.getCategory(name);
    	if(category != null && properties != null) {
    		//category.editElement(id,properties);
    	}    	
    }
    
    @GET
    @Path("category/new")
    public void newCategoryElements(@QueryParam("name") final String name, @QueryParam("headers") final String headers) {
    	loger.info("Add new category : '" + name + "' with headers : '" + headers + "'");
    	Category category = new Category(name);
    	category.setHeaders(headers);
    	model.addCategory(category);
    }
    
    @GET
    @Path("category/{name}")
    public String addCategoryElements(@PathParam("name") final String name) {
      return model.getCategoryElementsASJASON(name);
    }
    
    @GET
    @Path("categories")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCategories() {
      return model.getCategoriesAsJSON();
    }
    
    @GET
    @Path("item")
    @Produces({"application/xml"})
    public Category  getItem() {
     
      Category item = new Category();
     
     return item;
    } 
 
    
}