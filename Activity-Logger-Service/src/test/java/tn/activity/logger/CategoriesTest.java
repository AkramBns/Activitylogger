package tn.activity.logger;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tn.tests.logger.model.Model;

public class CategoriesTest {

	private Model mod = new Model();
	@BeforeSuite
	public void setUp() {
		mod.getCategories();
	}
	
	@Test
	public void testCategories() {
		
		System.out.println(mod.getCategoriesAsJSON());
	}
	
	@Test
	public void testNullCategory() {

		System.out.println(mod.getCategoryElementsASJASON(null));
	}
	
	@Test
	public void testCategoryElements() {
		mod.getCategories();
		System.out.println("Elements of 'category 1' \n" + mod.getCategoryElementsASJASON("category 2"));

	}
}
