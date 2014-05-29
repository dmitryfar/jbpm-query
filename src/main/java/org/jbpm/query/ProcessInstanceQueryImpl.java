package org.jbpm.query;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ProcessInstanceQueryImpl extends
		AbstractVariableQueryImpl<ProcessInstanceQuery> implements
		ProcessInstanceQuery, Serializable {
	
	private static final long serialVersionUID = 1L;

	protected Long processInstanceId;
	protected String processDefinitionId;
	protected String businessKey;
	protected boolean finished = false;
	protected boolean unfinished = false;
	protected String startedBy;
	protected Long parentProcessInstanceId;
	protected List<String> processKeyNotIn;
	protected Date startedBefore;
	protected Date startedAfter;
	protected Date finishedBefore;
	protected Date finishedAfter;
	protected String processDefinitionKey;
	protected Set<Long> processInstanceIds;

	public ProcessInstanceQueryImpl() {
		super();
	}
	public ProcessInstanceQueryImpl(EntityManager em) {
		super(em);
	}

	public ProcessInstanceQueryImpl processInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
		return this;
	}

	public ProcessInstanceQueryImpl processInstanceIds(
			Set<Long> processInstanceIds) {
		if (processInstanceIds == null) {
			throw new RuntimeException("Set of process instance ids is null");
		}
		if (processInstanceIds.isEmpty()) {
			throw new RuntimeException("Set of process instance ids is empty");
		}
		this.processInstanceIds = processInstanceIds;
		return this;
	}

	public ProcessInstanceQueryImpl processDefinitionId(
			String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
		return this;
	}

	public ProcessInstanceQueryImpl processDefinitionKey(
			String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
		return this;
	}

	public ProcessInstanceQueryImpl processInstanceBusinessKey(
			String businessKey) {
		this.businessKey = businessKey;
		return this;
	}

	public ProcessInstanceQueryImpl finished() {
		this.finished = true;
		return this;
	}

	public ProcessInstanceQueryImpl unfinished() {
		this.unfinished = true;
		return this;
	}

	public ProcessInstanceQueryImpl startedBy(String userId) {
		this.startedBy = userId;
		return this;
	}

	public ProcessInstanceQueryImpl processDefinitionKeyNotIn(
			List<String> processDefinitionKeys) {
		this.processKeyNotIn = processDefinitionKeys;
		return this;
	}

	public ProcessInstanceQueryImpl startedAfter(Date date) {
		startedAfter = date;
		return this;
	}

	public ProcessInstanceQueryImpl startedBefore(Date date) {
		startedBefore = date;
		return this;
	}

	public ProcessInstanceQueryImpl finishedAfter(Date date) {
		finishedAfter = date;
		finished = true;
		return this;
	}

	public ProcessInstanceQueryImpl finishedBefore(Date date) {
		finishedBefore = date;
		finished = true;
		return this;
	}

	public ProcessInstanceQueryImpl parentProcessInstanceId(
			Long parentProcessInstanceId) {
		this.parentProcessInstanceId = parentProcessInstanceId;
		return this;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public boolean isOpen() {
		return unfinished;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public String getProcessDefinitionIdLike() {
		return processDefinitionKey + ":%:%";
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public Set<Long> getProcessInstanceIds() {
		return processInstanceIds;
	}

	public String getStartedBy() {
		return startedBy;
	}

	public Long getParentProcessInstanceId() {
		return parentProcessInstanceId;
	}

	public void setParentProcessInstanceId(Long parentProcessInstanceId) {
		this.parentProcessInstanceId = parentProcessInstanceId;
	}

	public List<String> getProcessKeyNotIn() {
		return processKeyNotIn;
	}

	public Date getStartedAfter() {
		return startedAfter;
	}

	public Date getStartedBefore() {
		return startedBefore;
	}

	public Date getFinishedAfter() {
		return finishedAfter;
	}

	public Date getFinishedBefore() {
		return finishedBefore;
	}

	@Override
	public long executeCount(EntityManager em) {
		
		Query query = prepareQuery(em, getSqlCount());
		
		Long result = (Long) query.getSingleResult();
		
		return result;
	}
	
	public List<?> executeList(EntityManager em, Page page) {
		
		Query query = prepareQuery(em, getSqlSelect());
		
		// add pagination
		
		if (page != null) {
			query.setFirstResult(page.firstResult);
			query.setMaxResults(page.maxResults);
		}
		
		// add order by
        // addOrder("p.processInstanceId", "asc");
        //sb.append(" order by ").append(getOrderBy());
        
		// execute query
		
		List<?> result = query.getResultList();
		
		return result;
	}

	private String getSqlSelect() {
		String sql = getSql();
		sql = "select p " + sql;
		
		return sql;
	}
	private String getSqlCount() {
		String sql = getSql();
		sql = "select count(p) " + sql;
		
		return sql;
	}
	private String getSql() {
		StringBuilder sb = new StringBuilder();
        // sb.append("select p ");
        sb.append("FROM ProcessInstanceLog p ");
        
        if (queryVariableValues != null && queryVariableValues.size() > 0) {
        	int index = 0;
        	for (QueryVariableValue queryVariableValue : queryVariableValues) {
        		sb.append(", VariableInstanceCur v" + index + " ");
        		index++;
        	}
        }
        
        sb.append("WHERE 1=1 ");
        if (finishedAfter != null) {
        	sb.append(" AND p.end >= :end ");
        }
        if (finishedBefore != null) {
        	sb.append(" AND p.end <= :end ");
        }
        if (processDefinitionId != null) {
        	sb.append(" AND p.processId = :processDefinitionId ");
        }
        if (processInstanceId != null) {
        	sb.append(" AND p.processInstanceId = :processInstanceId ");
        }
        if (processInstanceIds != null) {
        	sb.append(" AND p.processInstanceId in (:processInstanceIds) ");
        }
        if (startedAfter != null) {
        	sb.append(" AND p.start >= :start ");
        }
        if (startedBefore != null) {
        	sb.append(" AND p.start <= :start ");
        }
        if (parentProcessInstanceId != null) {
        	sb.append(" AND p.parentProcessInstanceId = :parentProcessInstanceId ");
        }
        if (unfinished == true) {
        	sb.append(" AND p.end is null ");
        }
        if (finished == true) {
        	sb.append(" AND p.end is not null ");
        }
        
        if (queryVariableValues != null && queryVariableValues.size() > 0) {
        	int index = 0;
        	for (QueryVariableValue queryVariableValue : queryVariableValues) {
        		String operator = getVariableOperator(QueryOperator.valueOf(queryVariableValue.getOperator()));
        		StringBuilder sbVar = new StringBuilder();
        		sbVar.append(" AND p.processInstanceId = v${index}.processInstanceId "); sbVar.append(" AND v${index}.variableId = :variableId${index} ");
        		
        		// Null variable type
        		if (queryVariableValue.getValue() == null) {
        			if (queryVariableValue.getOperator().equals(QueryOperator.NOT_EQUALS.toString())) {
        				sbVar.append(" AND v${index}.value is not null ");
        			} else {
        				sbVar.append(" AND v${index}.value is null ");
        			}
        		} else {
	        		if (queryVariableValue.getOperator().equals(QueryOperator.EQUALS_IGNORE_CASE.toString()) 
	        				|| queryVariableValue.getOperator().equals(QueryOperator.NOT_EQUALS_IGNORE_CASE.toString())) {
	        			sbVar.append(" AND lower(v${index}.value) ");
	        		} else {
	        			sbVar.append(" AND v${index}.value ");
	        		}
	        		if (queryVariableValue.getOperator().equals(QueryOperator.LIKE.toString())) {
	        			sbVar.append("LIKE");
	        		} else {
	        			sbVar.append(operator);
	        		}
	        		if (queryVariableValue.getOperator().equals(QueryOperator.IN.toString())) {
	        			sbVar.append(" (:variableValue${index}) ");
	        		} else {
	        			sbVar.append(" :variableValue${index} ");
	        		}
        		}
        		
        		
        		String strVar = sbVar.toString().replace("${index}", String.valueOf(index));
        		
        		
        		
        		sb.append(strVar);
        		index++;
			}
        }
        
        // TODO: remove leadings AND and OR
        
        return sb.toString();
	}

	private static String getVariableOperator(QueryOperator op) {
    	switch (op) {
    	case IN: 						return "IN";
    	case EQUALS:					return "=";
		case EQUALS_IGNORE_CASE: 		return "=";
		case NOT_EQUALS: 				return "<>";
		case NOT_EQUALS_IGNORE_CASE: 	return "<>";
		case GREATER_THAN: 				return ">";
		case GREATER_THAN_OR_EQUAL: 	return ">=";
		case LESS_THAN: 				return "<";
		case LESS_THAN_OR_EQUAL: 		return "<=";
		
		default:						return "=";
		} 
    }
	
	private Query prepareQuery(EntityManager em, String sql) {
		// prepare sql
		
		//String sql = getSqlSelect();
		
		//sql = "SELECT RES FROM (" + sql + ") as RES";
		
		// create query
		
		Query query = em.createQuery(sql);
		
		// add parameters to query
		
        if (finishedAfter != null) {
        	query.setParameter("end", finishedAfter);
        }
        if (finishedBefore != null) {
        	query.setParameter("end", finishedBefore);
        }
        if (processDefinitionId != null) {
        	query.setParameter("processDefinitionId", processDefinitionId);
        }
        if (processInstanceId != null) {
        	query.setParameter("processInstanceId", processInstanceId);
        }
        if (processInstanceIds != null) {
        	query.setParameter("processInstanceIds", processInstanceIds);
        }
        if (startedAfter != null) {
        	query.setParameter("start", startedAfter);
        }
        if (startedBefore != null) {
        	query.setParameter("start", startedBefore);
        }
        if (parentProcessInstanceId != null) {
        	query.setParameter("parentProcessInstanceId", parentProcessInstanceId);
        }
		
		if (queryVariableValues != null && queryVariableValues.size() > 0) {
	    	int index = 0;
	    	for (QueryVariableValue queryVariableValue : queryVariableValues) {
	    		query.setParameter("variableId" + index, queryVariableValue.getName());
	    		// if equals null then don't use variable value because 
	    		// in this case sql is "variableValue is null" or "variableValue is not null"
	    		if (queryVariableValue.getValue() != null) {
	    			// while value is string field, we have to use cast to string there
	    			Object variableValue;
	    			if (queryVariableValue.getOperator().equals(QueryOperator.IN.toString())) {
	    				variableValue = convertArrayToCollectionIfNecessary(queryVariableValue.getValue());
	    			} else {
	    				variableValue = String.valueOf(queryVariableValue.getValue());
	    			}
	    			query.setParameter("variableValue" + index, variableValue);
	    		}
	    		index++;
	    	}
	    }
		
		return query;
	}
	
		/**
	 	 * In order to avoid errors like: IllegalArgumentException: Encountered array-valued parameter binding, but was
	 	 * expecting [java.lang.Integer].
	 	 * 
	 	 * @see ParameterBinder.java on github.com: <a href="https://github.com/spring-projects/spring-data-jpa/pull/45/commits">link</a>
	 	 * @throws Exception
	 	 */
	 	private Object convertArrayToCollectionIfNecessary(Object value) {
	 
	 		Object result = value;
	 
	 		if (result != null && result.getClass().isArray()) {
	 			int len = Array.getLength(value);
	 			Collection<Object> list = new ArrayList<Object>(len);
	 			for (int i = 0; i < len; i++) {
	 				list.add(Array.get(value, i));
	 			}
	 			result = list;
	  		}
	 
	 		return result;
	  	}
	
	public ProcessInstanceQuery setPageList(int firstResult, int maxResults) {
		return super.setListPage(firstResult, maxResults);
	}
	
	public String toString() {
		Field[] fields = getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		sb.append("ProcessInstanceQueryImpl:{");
		for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            
			String typeValue = null;
			try {
				String methodName = "get" + Character.toUpperCase(field.getName().charAt(0))
                    + field.getName().substring(1);
                typeValue  = String.valueOf( this.getClass().getMethod(methodName, (Class[]) null).invoke(this, new Object[]{}) );
            } catch (Exception e) {
                typeValue = "...";
            }
			sb.append(field.getName());
			sb.append(": ");
			sb.append(typeValue);
			if (i < fields.length-1) {
				sb.append(typeValue);
			}
		}
		sb.append("}");
		
		return sb.toString();
	}
}
