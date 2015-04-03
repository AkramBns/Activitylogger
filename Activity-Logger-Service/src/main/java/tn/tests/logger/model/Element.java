package tn.tests.logger.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;

/**
 * 
 * @author akrambns
 *
 */
@Entity
@Table(name = "Element")
public class Element {
	
	@Transient
	Logger logger = Logger.getLogger(Element.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the category of this element.
	 */
	@ManyToOne
	@JoinColumn(name = "category_Id")
	private Category parent;
	
	/**
	 * The kyes should match the associated category headers list
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<ElementProperty> properties = new ArrayList<ElementProperty>();

	@Column(name="sparator")
	private String fieldsSparator = ";";
	
	public Element() {

	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public void addProperties(String props) {
		if(props != null) {
			ElementProperty p;
			String[] popsParts = props.split(fieldsSparator);
			for (int i = 0; i < popsParts.length; i++) {
				String[] prop = popsParts[i].split("=");
				if(prop.length == 2) {
					p = new ElementProperty(prop[0], prop[1]);
					p.setParent(this);
					properties.add(p );
				}
			}
		}
	}


	public List<ElementProperty> getProperties() {
		return properties;
	}


	public void setProperties(List<ElementProperty> properties) {
		if(properties == null) {
			logger.warn("Null properties ignored!!");
			return;
		}
		
		for (ElementProperty elementProperty : properties) {
			elementProperty.setParent(this);			
			logger.info(" adding element property : " + elementProperty.getName() + " : " + elementProperty.getValue());
		}
		
		this.properties = properties;
		
	}


	public String getFieldsSparator() {
		return fieldsSparator;
	}


	public void setFieldsSparator(String fieldsSparator) {
		this.fieldsSparator = fieldsSparator;
	}


}
