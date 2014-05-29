package org.jbpm.query;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Abstract superclass for all query types.
 * 
 * @author Joram Barrez
 * @author Dmitry Farafonov
 */
public abstract class AbstractQuery<T> extends ListQueryParameterObject
		implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SORTORDER_ASC = "asc";
	public static final String SORTORDER_DESC = "desc";

	private static enum ResultType {
		LIST, LIST_PAGE, SINGLE_RESULT, COUNT
	}

	protected String orderBy;
	protected String orderDirection;

	protected ResultType resultType;

	protected QueryProperty orderProperty;

	private EntityManager em;

	public AbstractQuery(EntityManager em) {
		this.em = em;
	}

	public AbstractQuery() {
	}

	public T asc() {
		return direction(Direction.ASCENDING);
	}

	public T desc() {
		return direction(Direction.DESCENDING);
	}

	@SuppressWarnings("unchecked")
	public T direction(Direction direction) {
		if (orderProperty == null) {
			throw new RuntimeException(
					"You should call any of the orderBy methods first before specifying a direction");
		}
		addOrder(orderProperty.getName(), direction.getName());
		orderProperty = null;
		return (T) this;
	}

	protected void checkQueryOk() {
		if (orderProperty != null) {
			throw new RuntimeException(
					"Invalid query: call asc() or desc() after using orderByXX()");
		}
	}

	@SuppressWarnings("unchecked")
	public Object AbstractQuery() {
		this.resultType = ResultType.SINGLE_RESULT;
		if (em == null) {
			throw new RuntimeException(
					"Entity manager must be set before execution list query");
		}
		return executeSingleResult(em);
	}

	@SuppressWarnings("unchecked")
	public List<?> list() {
		this.resultType = ResultType.LIST;
		if (em == null) {
			throw new RuntimeException(
					"Entity manager must be set before the query execution");
		}
		return executeList(em, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<?> list(EntityManager em) {
		this.setEntityManager(em);
		if (this.resultType != null && this.resultType.equals(ResultType.LIST_PAGE)) {
			return listPage(this.firstResult, this.maxResults);
		}
		return list();
	}

	@SuppressWarnings("unchecked")
	public List<?> listPage(int start, int end) {
		this.firstResult = start;
		this.maxResults = end - start;
		this.resultType = ResultType.LIST_PAGE;
		if (em == null) {
			throw new RuntimeException(
					"Entity manager must be set before the query execution");
		}
		return executeList(em, new Page(firstResult, maxResults));
	}
	
	public T setListPage(int start, int end) {
		this.firstResult = start;
		this.maxResults = end - start;
		this.resultType = ResultType.LIST_PAGE;
		return (T) this;
	}

	public long count() {
		this.resultType = ResultType.COUNT;
		if (em == null) {
			throw new RuntimeException(
					"Entity manager must be set before the query execution");
		}
		return executeCount(em);
	}
	public long count(EntityManager em) {
		this.setEntityManager(em);
		return executeCount(em);
	}

	public Object execute(EntityManager em) {
		if (resultType == ResultType.LIST) {
			return executeList(em, null);
		} else if (resultType == ResultType.SINGLE_RESULT) {
			return executeSingleResult(em);
		} else if (resultType == ResultType.LIST_PAGE) {
			return executeList(em, null);
		} else {
			return executeCount(em);
		}
	}

	public abstract long executeCount(EntityManager em);

	/**
	 * Executes the actual query to retrieve the list of results.
	 * 
	 * @param page
	 *            used if the results must be paged. If null, no paging will be
	 *            applied.
	 */
	public abstract List<?> executeList(EntityManager em, Page page);

	public Object executeSingleResult(EntityManager em) {
		List<?> results = executeList(em, null);
		if (results.size() == 1) {
			return results.get(0);
		} else if (results.size() > 1) {
			throw new RuntimeException("Query return " + results.size()
					+ " results instead of max 1");
		}
		return null;
	}

	protected void addOrder(String column, String sortOrder) {
		if (orderBy == null) {
			orderBy = "";
		} else {
			orderBy = orderBy + ", ";
		}
		orderBy = orderBy + column + " " + sortOrder;
	}

	public String getOrderBy() {
		if (orderBy == null) {
			return super.getOrderBy();
		} else {
			return orderBy;
		}
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
}
