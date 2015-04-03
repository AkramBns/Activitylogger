package tn.tests.logger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


/**
 * 
 * A category corresponds to a gird. Each grid should have a defined headers.
 * 
 * @author akrambns
 *
 */
@Entity
@Table
public class Category  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	/**
	 * The name of the category
	 */
	@Column(name="name", unique=true)
	private String name;
	
	/**
	 * The headers of the grid associated to this category.
	 * Having the headers defined here is made to ensure that
	 * the data received is coherent and follow one same format.
	 */

	@ElementCollection
	private List<String> categoryHeaders = new ArrayList<String>();

	/**
	 * Default headers separator.
	 */
	@Column(name="separator")
	private String headersSeparator = ";";
	
	/**
	 * The elements associated to this category
	 */	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Element> categoryElements = new ArrayList<Element>();
	
	/**
	 * The default constructor
	 */
	public Category() {
		this.name = "Common";

	}
	
	
	/**
	 * Constructor
	 */
	public Category(String name) {
		this.name = name;

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	/**
	 * 
	 * @param hName :  a header to add to this category
	 */
	public void addHeader(String hName) {
		if(hName != null) {
			categoryHeaders.add(hName);	
		}	
	}
	
	/**
	 * 
	 * @param headers : A semicolon separated headers names
	 */
	public void setHeaders(String headers) {
		if(headers != null) {
			String[] hParts = headers.split(headersSeparator);
			for (int i = 0; i < hParts.length; i++) {
				categoryHeaders.add(hParts[i]);
			}
		}
	}
	
	public List<String> getCategoryHeaders() {
		return categoryHeaders;
	}

	public void setCategoryHeaders(List<String> categoryHeaders) {
		this.categoryHeaders = categoryHeaders;
	}

	public void addElement(Element ele) {
		if(ele != null) {
			ele.setParent(this);
			categoryElements.add(ele);
		}
	}
	

	public List<Element> getCategoryElements() {
		return categoryElements;
	}


	public void setCategoryElements(ArrayList<Element> categoryElements) {
		this.categoryElements = categoryElements;
	}

	public void setSeparator(String separator) {
		this.headersSeparator = separator;
	}

	public String getSeparator() {
		return headersSeparator;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getHeadersSeparator() {
		return headersSeparator;
	}


	public void setHeadersSeparator(String headersSeparator) {
		this.headersSeparator = headersSeparator;
	}


	public void setCategoryElements(List<Element> categoryElements) {
		this.categoryElements = categoryElements;
	}

	
	
}
