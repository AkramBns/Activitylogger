package tn.tests.logger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import tn.tests.logger.persist.HibernateUtil;



public class Model {

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
			String[] popsParts = elementDef
					.split(targetCategory.getSeparator());
			List<ElementProperty> properties = new ArrayList<ElementProperty>();
			for (int i = 0; i < popsParts.length; i++) {
				String[] prop = popsParts[i].split("=");
				if (prop.length == 2) {
					if (((ArrayList<String>) targetCategory.getCategoryHeaders()).contains(prop[0])) {
						// ensure that the property name is header of the current category
						properties.add(new ElementProperty(prop[0], prop[1]));
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

			List<String> headers = category.getCategoryHeaders();
			// add the id header
			colObj = new JSONObject();
			colObj = new JSONObject();
			colObj.put("field", "id");
			colObj.put("caption", "id");
			colObj.put("size", "100px");
			colObj.put("type", "int");
			
			columns.add(colObj);

			for (String string : headers) {
				colObj = new JSONObject();
				colObj.put("field", string);
				colObj.put("caption", string);
				colObj.put("size", "100px");
				colObj.put("type", "text");
				columns.add(colObj);
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
