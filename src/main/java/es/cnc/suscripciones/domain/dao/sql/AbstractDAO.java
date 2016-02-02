package es.cnc.suscripciones.domain.dao.sql;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import es.cnc.suscripciones.domain.AbstractEntity;
import es.cnc.suscripciones.domain.dao.IDAO;

public abstract class AbstractDAO<T extends AbstractEntity<I>, I extends Serializable> implements IDAO<T, I> {

	@PersistenceContext
    private transient EntityManager entityManager;
    private Class<T> clazz;
    
    /**
     * Fields belong this List can be used for sorting.
     * @return List with field names
     */
    protected abstract List<String> getFieldNames4OrderClauseFilter();
    
    public AbstractDAO() {
		super();
	}

    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#setClazz(java.lang.Class)
	 */
    @Override
	public void setClazz(Class<T> clazz) {
    	this.clazz = clazz;
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#entityManager()
	 */
    @Override
	public final EntityManager entityManager() {
    	if (entityManager == null)
    		throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return entityManager;
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#findById(I)
	 */
    @Override
	public T findById(I id) {
    	return this.entityManager().find(clazz, id);
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#findAll()
	 */
    @Override
	public List<T> findAll() {
    	return entityManager().createQuery(defaultFrom(),clazz).getResultList();
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#findAll(java.lang.String, java.lang.String)
	 */
    @Override
	public List<T> findAll(String sortFieldName, String sortOrder) {
        String jpaQuery = defaultQuery();
        if (getFieldNames4OrderClauseFilter().contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return findEntries(jpaQuery);
    }
    
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#findEntries(int, int)
	 */
    @Override
	public List<T> findEntries(int firstResult, int maxResults) {
        return findEntries(defaultQuery(), firstResult, maxResults); 

    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#findEntries(int, int, java.lang.String, java.lang.String)
	 */
    @Override
	public List<T> findEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = defaultQuery();
        if (getFieldNames4OrderClauseFilter().contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return findEntries(jpaQuery, firstResult, maxResults); 
    }
    
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#count()
	 */
    @Override
	public long count() {
        return entityManager().createQuery("SELECT COUNT(o) " + defaultFrom() +" o", Long.class).getSingleResult();
    }
    
    private List<T> findEntries(String jpaQuery) {
        return entityManager().createQuery(jpaQuery, clazz).getResultList();
    }
    
    private List<T> findEntries(String jpaQuery, int firstResult, int maxResults) {
        return entityManager().createQuery(jpaQuery, clazz).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    private String defaultQuery() {
    	return "SELECT o " +defaultFrom() +" o";
    }
    
    private String defaultFrom() {
    	return "FROM " + clazz.getName();
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#persist()
	 */
    @Override
	@Transactional
    public void persist() {
        this.entityManager().persist(this);
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#flush()
	 */
    @Override
	@Transactional
    public void flush() {
        entityManager().flush();
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#clear()
	 */
    @Override
	@Transactional
    public void clear() {
        this.entityManager().clear();
    }
    
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#remove(T)
	 */
    @Override
	@Transactional
    public void remove(T entity) {
        if (this.entityManager().contains(this)) {
            this.entityManager().remove(this);
        } else {
            T attached = findById((I)entity.getId());
            this.entityManager().remove(attached);
        }
    }
    
    /* (non-Javadoc)
	 * @see es.cnc.suscripciones.domain.dao.sql.IDAO#merge(T)
	 */
    @Override
	@Transactional
    public T merge(T entity) {
    	T merged = this.entityManager().merge(entity);
        this.entityManager().flush();
        return merged;
    }
    

    
}
