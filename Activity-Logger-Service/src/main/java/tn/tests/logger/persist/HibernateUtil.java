package tn.tests.logger.persist;

import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tn.tests.logger.model.Category;
import tn.tests.logger.model.Element;
import tn.tests.logger.model.ElementProperty;

  
public class HibernateUtil {
  
    private static final SessionFactory sessionFactory = buildSessionFactory();
      
    
    private static SessionFactory buildSessionFactory() {
        try {
        	/*
            // Create the SessionFactory from hibernate.cfg.xml
        	   Configuration configuration = new Configuration();
        	    configuration.configure();
        	    ServiceRegistry serviceRegistry =  new StandardServiceRegistryBuilder().applySettings(
        	            configuration.getProperties()).build();
        	    return configuration.buildSessionFactory(serviceRegistry);
        	  */
        	Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                    configuration.getProperties()). buildServiceRegistry();
            
            return  configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
  
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
  
    public void save(Object category) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.save(category);
    	session.getTransaction().commit();
    }
    
    public void persistCategory(Category category) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.save(category);
    	session.getTransaction().commit();
    }

    public List<Category> getCategories() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	String hql = "FROM Category";
    	Query query = session.createQuery(hql);
    	List<Category> results = query.list();
    	session.getTransaction().commit();
    	return results;
    }
    
    public List<Element> getCategoryElements(int categoryId) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	Query query = session.createQuery("from Category where id = :id ");
    	query.setParameter("id", categoryId);
    	List<Element> results = query.list();
    	
    	session.getTransaction().commit();
    	
    	return results;
    }

    public List<Element> getCategoryElementsByName(String categoryName) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	Query query = session.createQuery("from Element e join e.parent c where c.name = :name ");
    	query.setParameter("name", categoryName);
    	List<Object[]> results = query.list();
    	
    	ArrayList<Element> retList = new ArrayList<Element>();
    	for (Object[] resultObj : results) {
    		
    		retList.add((Element) resultObj[0]);
		}
    	
    	session.getTransaction().commit();
    	
    	return retList;
    }
    
	public void persistElement(Element item1) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.save(item1);
    	session.getTransaction().commit();
    	String hql = "FROM Element";
    	Query query = session.createQuery(hql);
    	List<Element> results = query.list();
    	System.out.println(results.size());
    	System.out.println(results.get(0).getProperties());
    	
	}
	
	public void saveElementProperty(ElementProperty prop) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.save(prop);
    	session.getTransaction().commit();
    }

	public void persistElement(List<ElementProperty> properties) {
		for (ElementProperty elementProperty : properties) {
			saveElementProperty(elementProperty);
		}
		
	}
	
	
}