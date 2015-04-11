package tn.tests.logger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import tn.tests.logger.persist.HibernateUtil;



public class Model {

	public static final String Details_Column_Caption = "details";
	
	public static final String CATEGORY_Caption_MAPPING = "Col_";
	

	Logger logger = Logger.getLogger(Model.class);
	
	private ArrayList<Category> list = new ArrayList<Category>();

	private HibernateUtil hutils = new HibernateUtil();
	
	public Model() {


	}

	public List<Category> getCategories() {
		
		return hutils.getCategories();
	}

	public String getCategoriesAsJSON() {

		JSONArray ja = new JSONArray();
		int id = 0;
		for (Category cat : getCategories()) {

			JSONObject js = new JSONObject();
			if (id == 0) {
				js.put("selected", "true");
			}
			js.put("id", cat.getName());
			js.put("text", cat.getName());
			ja.add(js);
			id += 1;
		}

		System.out.println("Categories :" + ja.toJSONString());
		return ja.toJSONString();
	}

	public Category getCategory(String catName) {

		List<Category> category = getCategories();
		for (Category cat : category) {
			if (cat.getName().equals(catName)) {
				return cat;
			}
		}

		return null;
	}

	/**
	 * Parses and add an element to this category. TODO : notify in case the
	 * element is not added
	 * 
	 * @param elementDef
	 * @return !
	 */
	public Element parseElement(Category targetCategory, String elementDef) {

		Element ele = null;
		if (elementDef != null) {
			
			List<ElementProperty> properties = new ArrayList<ElementProperty>();
			
			
			String[] popsParts = elementDef
					.split(targetCategory.getSeparator());
			
			for (int i = 0; i < popsParts.length; i++) {
				String[] prop = popsParts[i].split("=");
				if (prop.length == 2) {

					// ensure that the property name is header of the current category					
					if ((targetCategory.getCategoryHeaders()).contains(prop[0])) {						
						properties.add(new ElementProperty(targetCategory.getHeaderMapping().get(prop[0]), prop[1]));
					} else {
						// in case of invalid header name, do not add the
						// element
						// this will avoid adding wrong data
						logger.warn("One of the element properties is not recognized by the target category and hence will be ignored!");
					}
				}
			}

			ele = new Element();
			ele.setProperties(properties);
			targetCategory.addElement(ele);
		} else {
			logger.error("Can not add a null element to a category!!");
		}

		return ele;
	}

	/**
	 * 
	 * @param catName
	 *            : name of the category
	 * @return JSON representation of the category elements
	 */
	public String getCategoryElementsASJASON(String catName) {

		Category category = null;
		JSONObject jo = new JSONObject();
		JSONArray records = new JSONArray();
		JSONArray columns = new JSONArray();
		JSONObject colObj;

		int id = 0;

		if (catName != null) {
			category = getCategory(catName);
		}

		if (category == null) {

		} else {

			 Iterator<String> headersItr = category.getHeaderMapping().keySet().iterator();
			// add the id header
			colObj = new JSONObject();
			colObj = new JSONObject();
			colObj.put("field", "id");
			colObj.put("caption", "id");
			colObj.put("size", "100px");
			colObj.put("type", "int");
			colObj.put("html", "{ caption: 'ID', attr: 'size=\"10\" readonly' }");
  
			columns.add(colObj);
			int colIdx = 0;
			while (headersItr.hasNext()) {
				String headerCaption = (String) headersItr.next();
				
				if(headerCaption.equalsIgnoreCase(Model.Details_Column_Caption)) {
					continue;
				}
				colObj = new JSONObject();				
				//get the field id associated to this caption
				colObj.put("field", category.getHeaderMapping().get(headerCaption));
				colObj.put("caption", headerCaption);
				colObj.put("size", "100px");
				colObj.put("type", "text");
				colObj.put("html", "{ caption: '" + headerCaption + "', attr: 'size=\"10\"' } ");	
				columns.add(colObj);
				colIdx +=1;
			}
			
			
			Collection<Element> elements = category.getCategoryElements();

			Enumeration<String> propKeys;
			for (Element element : elements) {
				JSONObject js = new JSONObject();
				js.put("recid", id);
				List<ElementProperty> propList = element.getProperties();
				for (ElementProperty elementProperty : propList) {					
					js.put(elementProperty.getName(), elementProperty.getValue());	
				}
				js.put("id", element.getId());
				records.add(js);
				id += 1;
			}
		}

		jo.put("columns", columns);
		jo.put("records", records);
		return jo.toJSONString();
	}

	public void addCategory(Category category) {
		list.add(category);

	}

}
