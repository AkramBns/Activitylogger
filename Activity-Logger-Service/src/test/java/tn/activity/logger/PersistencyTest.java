package tn.activity.logger;

import java.util.Iterator;
import java.util.List;

import org.apache.velocity.tools.config.SkipSetters;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tn.tests.logger.model.Category;
import tn.tests.logger.model.Element;
import tn.tests.logger.model.ElementProperty;
import tn.tests.logger.model.Model;
import tn.tests.logger.persist.HibernateUtil;

public class PersistencyTest {

	private Model mod = new Model();
	@BeforeSuite
	public void setUp() {
		
		
	}
	
	@Test (priority=0)	
	public void saveCategories() {
		
		Category item1 = new Category("category 1");		
		item1.setHeaders("col1;col2;col3;col4;details");
		
		mod.parseElement(item1,"col1=1value11;col2=1value12;col3=1value13;col4=1value14;details=no details available");
		mod.parseElement(item1,"col1=1value21;col2=1value22;col3=1value23;col4=1value24;details=no details available");
		mod.parseElement(item1,"col1=1value31;col2=1value32;col3=1value33;col4=1value34;details=no details available");
		mod.parseElement(item1,"col1=1value41;col2=1value42;col3=1value43;col4=1value44;details=no details available");

		Category item2 = new Category("category 2");
		item2.setHeaders("col1;col2;col3;col4;details");
		
		mod.parseElement(item2,"col1=2value11;col2=2value12;col3=2value31;col4=2value41;details=no details available");
		mod.parseElement(item2,"col1=2value21;col2=2value22;col3=2value32;col4=2value42;details=no details available");
		mod.parseElement(item2,"col1=2value31;col2=2value32;col3=2value33;col4=2value43;details=no details available");

		Category item3 = new Category("category 3");
		item3.setHeaders("col1;col2;col3;details");
		mod.parseElement(item3,"col1=3value11;col2=3value12;col3=3value13;details=no details available");
		mod.parseElement(item3,"col1=3value21;col2=3value22;col3=3value23;details=no details available");
		mod.parseElement(item3,"col1=3value31;col2=3value32;col3=3value33;details=no details available");
		
		HibernateUtil hutils = new HibernateUtil();
		
		hutils.persistCategory(item1);
		hutils.persistCategory(item2);
		hutils.persistCategory(item3);
	}
	
	@Test (priority=1)
	public void getCategories() {
		HibernateUtil hutils = new HibernateUtil();
		List<Category> catList = hutils.getCategories();
		for (Category category : catList) {
			System.out.println(">>>>>>>>>>>>>>>>> category : " + category.getName());
			Iterator<String> itr = category.getHeaderMapping().keySet().iterator();
			while (itr.hasNext()) {
				String string = (String) itr.next();
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> " + string + ":" + category.getHeaderMapping().get(string));
			}
			
		}
	}
	
	@Test (priority=2)
	public void getCategoryElements() {
		HibernateUtil hutils = new HibernateUtil();

		List<Element> elements = hutils.getCategoryElementsByName("category 1");
		
		for (Element element : elements) {
			List<ElementProperty> props = element.getProperties();			
			
			for (ElementProperty elementProperty : props) {
				System.out.print(elementProperty.getName() + "=" + elementProperty.getValue() +",");
			}
			System.out.println();
			
		}
		
		
	}
	
}
