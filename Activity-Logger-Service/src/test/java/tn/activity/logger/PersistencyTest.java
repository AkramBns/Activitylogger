package tn.activity.logger;

import java.util.List;

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
		
		Category item1 = new Category("category 5");
		item1.setHeaders("col1;col2;col3;col4;details");
		mod.parseElement(item1,"col1=qq;col2=bbb;col3=aaa;col4=sss;details;id123123");
		mod.parseElement(item1,"col1=value1;col2=value2;col3=value3;col4=value4;details=no details available;id12343");
		mod.parseElement(item1,"col1=value12;col2=value22;col3=value32;col4=value42;details=no details available;id=2");
		mod.parseElement(item1,"col1=value13;col2=value23;col3=value33;col4=value43;details=no details available;id=3");
		mod.parseElement(item1,"col1=value14;col2=value24;col3=value34;col4=value44;details=no details available;id=4");

		Category item2 = new Category("category 2");
		item2.setHeaders("col1;col2;col3;col4;details");
		mod.parseElement(item2,"col1;col2;col3;col4;details");
		mod.parseElement(item2,"col1=2value1;col2=2value2;col3=2value3;col4=2value4;details=no details available");
		mod.parseElement(item2,"col1=2value12;col2=2value22;col3=2value32;col4=2value42;details=no details available");
		mod.parseElement(item2,"col1=2value13;col2=2value23;col3=2value33;col4=2value43;details=no details available");

		Category item3 = new Category("category 3");
		item3.setHeaders("col1;col2;col3;details");
		mod.parseElement(item3,"col1;col2;col3;details");
		mod.parseElement(item3,"col1=3value1;col2=3value2;col3=3value3;details=no details available");
		mod.parseElement(item3,"col1=3value12;col2=3value22;col3=3value32;details=no details available");
		
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
			System.out.println(">>>>>>>>>>>>>>>>>category " + category.getName());
		}
	}
	
	@Test (priority=1)
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
