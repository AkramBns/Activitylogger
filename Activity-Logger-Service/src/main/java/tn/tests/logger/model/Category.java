package tn.tests.logger.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
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
	private String name = "Common";
	
	/**
	 * The headers of the grid associated to this category.
	 * Having the headers defined here is made to ensure that
	 * the data received is coherent and follow one same format.
	 */

	@ElementCollection
	private Set<String> categoryHeaders = new HashSet<String>();

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
	 * As we will have different configurations of categories, mapping the grid
	 * columns captions with a knows columns names will permit us to proper
	 * handler the POST requests that will edit the grid table. We have known
	 * POST fields names and we can map them with our model.
	 * 
	 */
	@ElementCollection // this is a collection of primitives
	@MapKeyColumn(name="key") // column name for map "key"
	@Column(name="value") // column name for map "value"
	private Map<String,String> headerMapping = new Hashtable<String, String>();
	  
	/**
	 * The default constructor
	 */
	public Category() {
		// all categories has details field
		categoryHeaders.add(Model.Details_Column_Caption);
		headerMapping.put(Model.Details_Column_Caption, Model.Details_Column_Caption);
		
	}
	
	
	/**
	 * Constructor
	 */
	public Category(String name) {
		this();
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
		if(hName != null && !hName.equalsIgnoreCase(Model.Details_Column_Caption)) {
			categoryHeaders.add(hName);
			int mappingSize = headerMapping.size() + 1;
			headerMapping.put(hName,Model.CATEGORY_Caption_MAPPING + mappingSize);
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
				addHeader(hParts[i]);
			}
		}
	}
	
	public Set<String> getCategoryHeaders() {
		return categoryHeaders;
	}

	public void setCategoryHeaders(Set<String> categoryHeaders) {
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
		for (Element element : categoryElements) {
			addElement(element);
		}		
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


	public Map<String, String> getHeaderMapping() {
		return headerMapping;
	}


	public void setHeaderMapping(Map<String, String> headerMapping) {
		this.headerMapping = headerMapping;
	}


	
	
}
