/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.query;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jbpm.task.Status;


/**
 * @author Joram Barrez
 * @author Tom Baeyens
 * @author Falko Menge
 * @author Tijs Rademakers
 * @author Dmitry Farafonov
 */
public class TaskQueryImpl extends AbstractQuery<TaskQuery> implements TaskQuery, Serializable {
  
  private static final long serialVersionUID = 1L;
  protected String taskId;
  protected String name;
  protected String nameLike;
  protected String description;
  protected String descriptionLike;
  protected Integer priority;
  protected Integer minPriority;
  protected Integer maxPriority;
  protected String assignee;
  protected String involvedUser;
  protected String owner;
  protected boolean unassigned = false;
  protected boolean noDelegationState = false;
//  protected DelegationState delegationState;
  protected String candidateUser;
  protected String candidateGroup;
  private List<String> candidateGroups;
  protected Long processInstanceId;
  protected String executionId;
  protected Date createTime;
  protected Date createTimeBefore;
  protected Date createTimeAfter;
  protected String key;
  protected String keyLike;
  protected String processDefinitionKey;
  protected String processDefinitionId;
  protected String processDefinitionName;
  protected String processInstanceBusinessKey;
  protected List<TaskQueryVariableValue> variables = new ArrayList<TaskQueryVariableValue>();
  protected Date dueDate;
  protected Date dueBefore;
  protected Date dueAfter;
  protected boolean withoutDueDate = false;
//  protected SuspensionState suspensionState;
  protected boolean excludeSubtasks = false;
  protected boolean includeTaskLocalVariables = false;
  protected boolean includeProcessVariables = false;
  
  // jbpm's assignees
  protected String actualOwner;
  protected ASSIGNEE_TYPE assigneeType;
  protected String businessAdministrator;
  protected String excludedOwner;
  protected String potentialOwner;
  protected String recipient;
  
  protected String language;
  protected boolean archived = false;
  protected List<Status> status = null;
  protected Date activationTime;
  protected Long workItemId;

  public TaskQueryImpl() {
    super();
  }
  
  public TaskQueryImpl(EntityManager em) {
    super(em);
  }
  
  public TaskQueryImpl taskId(String taskId) {
    if (taskId == null) {
      throw new RuntimeException("Task id is null");
    }
    this.taskId = taskId;
    return this;
  }
  
  public TaskQueryImpl taskName(String name) {
    if (name == null) {
      throw new RuntimeException("Task name is null");
    }
    this.name = name;
    return this;
  }
  
  public TaskQueryImpl taskNameLike(String nameLike) {
    if (nameLike == null) {
      throw new RuntimeException("Task namelike is null");
    }
    this.nameLike = nameLike;
    return this;
  }
  
  public TaskQueryImpl taskDescription(String description) {
    if (description == null) {
      throw new RuntimeException("Description is null");
    }
    this.description = description;
    return this;
  }
  
  public TaskQuery taskDescriptionLike(String descriptionLike) {
    if (descriptionLike == null) {
      throw new RuntimeException("Task descriptionlike is null");
    }
    this.descriptionLike = descriptionLike;
    return this;
  }
  
  public TaskQuery taskPriority(Integer priority) {
    if (priority == null) {
      throw new RuntimeException("Priority is null");
    }
    this.priority = priority;
    return this;
  }

  public TaskQuery taskMinPriority(Integer minPriority) {
    if (minPriority == null) {
      throw new RuntimeException("Min Priority is null");
    }
    this.minPriority = minPriority;
    return this;
  }

  public TaskQuery taskMaxPriority(Integer maxPriority) {
    if (maxPriority == null) {
      throw new RuntimeException("Max Priority is null");
    }
    this.maxPriority = maxPriority;
    return this;
  }

  public TaskQueryImpl taskAssignee(String assignee) {
    if (assignee == null) {
      throw new RuntimeException("Assignee is null");
    }
    this.assignee = assignee;
    return this;
  }
  
  public TaskQueryImpl taskAssignee(String assignee, TaskQuery.ASSIGNEE_TYPE assigneeType) {
	  if (assignee == null) {
		  throw new RuntimeException("Assignee is null");
	  }
	  /*
	  switch (assigneeType) {
	  	case BusinessAdministrator: 
	  		this.businessAdministrator = assignee;
	  		break;
	  	case ExcludedOwner: 
	  		this.excludedOwner = assignee;
	  		break;
	  	case PotentialOwner: 
	  		this.potentialOwner = assignee;
	  		break;
	  	case Recipient: 
	  		this.recipient = assignee;
	  		break;
	  	default: 
	  		this.assignee = assignee;
	  }
	  */
	  this.assigneeType = assigneeType;
	  this.assignee = assignee;
	  return this;
  }
  
  public TaskQueryImpl taskOwner(String owner) {
    if (owner == null) {
      throw new RuntimeException("Owner is null");
    }
    this.owner = owner;
    return this;
  }
  
  /** @see {@link #taskUnassigned} */
  @Deprecated
  public TaskQuery taskUnnassigned() {
    return taskUnassigned();
  }

  public TaskQuery taskUnassigned() {
    this.unassigned = true;
    return this;
  }

  /*public TaskQuery taskDelegationState(DelegationState delegationState) {
    if (delegationState == null) {
      this.noDelegationState = true;
    } else {
      this.delegationState = delegationState;
    }
    return this;
  }*/

  public TaskQueryImpl taskCandidateUser(String candidateUser) {
    if (candidateUser == null) {
      throw new RuntimeException("Candidate user is null");
    }
    if (candidateGroup != null) {
      throw new RuntimeException("Invalid query usage: cannot set both candidateUser and candidateGroup");
    }
    if (candidateGroups != null) {
      throw new RuntimeException("Invalid query usage: cannot set both candidateUser and candidateGroupIn");
    }
    this.candidateUser = candidateUser;
    return this;
  }
  
  public TaskQueryImpl taskInvolvedUser(String involvedUser) {
    if (involvedUser == null) {
      throw new RuntimeException("Involved user is null");
    }
    this.involvedUser = involvedUser;
    return this;
  }
  
  public TaskQueryImpl taskCandidateGroup(String candidateGroup) {
    if (candidateGroup == null) {
      throw new RuntimeException("Candidate group is null");
    }
    if (candidateUser != null) {
      throw new RuntimeException("Invalid query usage: cannot set both candidateGroup and candidateUser");
    }
    if (candidateGroups != null) {
      throw new RuntimeException("Invalid query usage: cannot set both candidateGroup and candidateGroupIn");
    }
    this.candidateGroup = candidateGroup;
    return this;
  }
  
  public TaskQuery taskCandidateGroupIn(List<String> candidateGroups) {
    if(candidateGroups == null) {
      throw new RuntimeException("Candidate group list is null");
    }
    if(candidateGroups.size()== 0) {
      throw new RuntimeException("Candidate group list is empty");
    }
    
    if (candidateUser != null) {
      throw new RuntimeException("Invalid query usage: cannot set both candidateGroupIn and candidateUser");
    }
    if (candidateGroup != null) {
      throw new RuntimeException("Invalid query usage: cannot set both candidateGroupIn and candidateGroup");
    }
    
    this.candidateGroups = candidateGroups;
    return this;
  }
  
  public TaskQueryImpl processInstanceId(Long processInstanceId) {
    this.processInstanceId = processInstanceId;
    return this;
  }
  
  public TaskQueryImpl processInstanceBusinessKey(String processInstanceBusinessKey) {
    this.processInstanceBusinessKey = processInstanceBusinessKey;
    return this;
  }
  
  public TaskQueryImpl executionId(String executionId) {
    this.executionId = executionId;
    return this;
  }
  
  public TaskQueryImpl taskCreatedOn(Date createTime) {
    this.createTime = createTime;
    return this;
  }
  
  public TaskQuery taskCreatedBefore(Date before) {
    this.createTimeBefore = before;
    return this;
  }
  
  public TaskQuery taskCreatedAfter(Date after) {
    this.createTimeAfter = after;
    return this;
  }
  
  public TaskQuery taskDefinitionKey(String key) {
    this.key = key;
    return this;
  }
  
  public TaskQuery taskDefinitionKeyLike(String keyLike) {
    this.keyLike = keyLike;
    return this;
  }
  
  public TaskQuery taskVariableValueEquals(String variableName, Object variableValue) {
    variables.add(new TaskQueryVariableValue(variableName, variableValue, QueryOperator.EQUALS, true));
    return this;
  }
  
  public TaskQuery taskVariableValueEquals(Object variableValue) {
    variables.add(new TaskQueryVariableValue(null, variableValue, QueryOperator.EQUALS, true));
    return this;
  }
  
  public TaskQuery taskVariableValueEqualsIgnoreCase(String name, String value) {
    if(value == null) {
      throw new RuntimeException("value is null");
    }
    variables.add(new TaskQueryVariableValue(name, value.toLowerCase(), QueryOperator.EQUALS_IGNORE_CASE, true));
    return this;
  }
  
  public TaskQuery taskVariableValueNotEqualsIgnoreCase(String name, String value) {
    if(value == null) {
      throw new RuntimeException("value is null");
    }
    variables.add(new TaskQueryVariableValue(name, value.toLowerCase(), QueryOperator.NOT_EQUALS_IGNORE_CASE, true));
    return this;
  }

  public TaskQuery taskVariableValueNotEquals(String variableName, Object variableValue) {
    variables.add(new TaskQueryVariableValue(variableName, variableValue, QueryOperator.NOT_EQUALS, true));
    return this;
  }

  public TaskQuery processVariableValueEquals(String variableName, Object variableValue) {
    variables.add(new TaskQueryVariableValue(variableName, variableValue, QueryOperator.EQUALS, false));
    return this;
  }

  public TaskQuery processVariableValueNotEquals(String variableName, Object variableValue) {
    variables.add(new TaskQueryVariableValue(variableName, variableValue, QueryOperator.NOT_EQUALS, false));
    return this;
  }
  
  public TaskQuery processVariableValueEquals(Object variableValue) {
    variables.add(new TaskQueryVariableValue(null, variableValue, QueryOperator.EQUALS, false));
    return this;
  }
  
  public TaskQuery processVariableValueEqualsIgnoreCase(String name, String value) {
    if(value == null) {
      throw new RuntimeException("value is null");
    }
    variables.add(new TaskQueryVariableValue(name, value.toLowerCase(), QueryOperator.EQUALS_IGNORE_CASE, false));
    return this;
  }
  
  public TaskQuery processVariableValueNotEqualsIgnoreCase(String name, String value) {
    if(value == null) {
      throw new RuntimeException("value is null");
    }
    variables.add(new TaskQueryVariableValue(name, value.toLowerCase(), QueryOperator.NOT_EQUALS_IGNORE_CASE, false));
    return this;
  }

  public TaskQuery processDefinitionKey(String processDefinitionKey) {
    this.processDefinitionKey = processDefinitionKey;
    return this;
  }

  public TaskQuery processDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
    return this;
  }
  
  public TaskQuery processDefinitionName(String processDefinitionName) {
    this.processDefinitionName = processDefinitionName;
    return this;
  }
  
  public TaskQuery dueDate(Date dueDate) {
    this.dueDate = dueDate;
    this.withoutDueDate = false;
    return this;
  }
  
  public TaskQuery dueBefore(Date dueBefore) {
    this.dueBefore = dueBefore;
    this.withoutDueDate = false;
    return this;
  }
  
  public TaskQuery dueAfter(Date dueAfter) {
    this.dueAfter = dueAfter;
    this.withoutDueDate = false;
    return this;
  }
  
  public TaskQuery withoutDueDate() {
    this.withoutDueDate = true;
    return this;
  }

  public TaskQuery excludeSubtasks() {
    this.excludeSubtasks = true;
    return this;
  }
  
  // JBPM
  
  public TaskQuery language(String language) {
	  this.language = language;
	  return this;
  }
  
  public TaskQuery archived() {
	  this.archived = true;
	  return this;
  }
  
  public TaskQuery taskCompleted(Boolean completed) {
	  if (completed != null) {
		  status = new ArrayList<Status>();
		  if (completed) {
			  status.add(Status.Completed);
		  } else {
			  status.add(Status.Ready);
			  status.add(Status.Reserved);
		  }
	  }
	  return this;
  }
  
/* -- commented by atr
 * if we'll be needed search by status - make something with this, 
 * because JBPM doesn't accept String values as Status
 * (throws exception about incompatible types)
 * 
 *  public TaskQuery taskStatus(String status) {
	  this.status.clear();
	  this.status.add(status);
	  return this;
  }
  
  public TaskQuery taskStatus(List<String> status) {
	  this.status.clear();
	  this.status.addAll(status);
	  return this;
  }*/
  
  public TaskQuery actualOwner(String actualOwner) {
	  this.actualOwner = actualOwner;
	  return this;
  }
  
  public TaskQuery taskWorkItemId(long workItemId) {
	  this.workItemId = workItemId;
	  return this;
  }
  
  // TODO: suspended and active (something more?)
/*  public TaskQuery suspended() {
    this.suspensionState = SuspensionState.SUSPENDED;
    return this;
  }

  public TaskQuery active() {
    this.suspensionState = SuspensionState.ACTIVE;
    return this;
  }*/
  
  public TaskQuery includeTaskLocalVariables() {
    this.includeTaskLocalVariables = true;
    return this;
  }
  
  public TaskQuery includeProcessVariables() {
    this.includeProcessVariables = true;
    return this;
  }

/*  public List<String> getCandidateGroups() {
    if (candidateGroup!=null) {
      List<String> candidateGroupList = new java.util.ArrayList<String>(1);
      candidateGroupList.add(candidateGroup);
      return candidateGroupList;
    } else if (candidateUser != null) {
      return getGroupsForCandidateUser(candidateUser);
    } else if(candidateGroups != null) {
      return candidateGroups;
    }
    return null;
  }*/
  
/*  protected void ensureVariablesInitialized() {    
    VariableTypes types = Context.getProcessEngineConfiguration().getVariableTypes();
    for(QueryVariableValue var : variables) {
      var.initialize(types);
    }
  }*/

  // TODO: ordering ////////////////////////////////////////////////////////////////
  
  /*public TaskQuery orderByTaskId() {
    return orderBy(TaskQueryProperty.TASK_ID);
  }
  
  public TaskQuery orderByTaskName() {
    return orderBy(TaskQueryProperty.NAME);
  }
  
  public TaskQuery orderByTaskDescription() {
    return orderBy(TaskQueryProperty.DESCRIPTION);
  }
  
  public TaskQuery orderByTaskPriority() {
    return orderBy(TaskQueryProperty.PRIORITY);
  }
  
  public TaskQuery orderByProcessInstanceId() {
    return orderBy(TaskQueryProperty.PROCESS_INSTANCE_ID);
  }
  
  public TaskQuery orderByExecutionId() {
    return orderBy(TaskQueryProperty.EXECUTION_ID);
  }
  
  public TaskQuery orderByTaskAssignee() {
    return orderBy(TaskQueryProperty.ASSIGNEE);
  }
  
  public TaskQuery orderByTaskCreateTime() {
    return orderBy(TaskQueryProperty.CREATE_TIME);
  }
  
  public TaskQuery orderByDueDate() {
    return orderBy(TaskQueryProperty.DUE_DATE);
  }
  
  public String getMssqlOrDB2OrderBy() {
    String specialOrderBy = super.getOrderBy();
    if (specialOrderBy != null && specialOrderBy.length() > 0) {
      specialOrderBy = specialOrderBy.replace("RES.", "TEMPRES_");
    }
    return specialOrderBy;
  }*/
  
  //results ////////////////////////////////////////////////////////////////

	public long executeCount(EntityManager em) {
		
		Query query = prepareQuery(em, getStatementCount());
		
		Long result = (Long) query.getSingleResult();
		
		return result;
	}
  
	public List<?> executeList(EntityManager em, Page page) {
		
		if (includeTaskLocalVariables || includeProcessVariables) {
			// TODO: do we have to use includeTaskLocalVariables and includeProcessVariables in SQL?
		}

		Query query = prepareQuery(em, getStatement());
		
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
  
	private String getStatement() {
		StringBuilder sb = new StringBuilder();
		sb.append("select t ");
        sb.append(getStatementFrom());
        sb.append(getStatementWhere());
        
		return sb.toString();
	}
	private String getStatementCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(t) ");
		sb.append(getStatementFrom());
		sb.append(getStatementWhere());
		
		return sb.toString();
	}
	
	private String getStatementFrom() {
		StringBuilder sb = new StringBuilder();
        // TODO: implement getSql for Task
        sb.append("from ");
        sb.append("Task t  ");
        sb.append("left join t.taskData.actualOwner as actualOwner    ");
		sb.append("left join t.taskData.createdBy as createdBy ");
//		sb.append("left join t.taskVariable as taskVariable ");
		sb.append("left join t.subjects as subject ");
        sb.append("left join t.descriptions as description ");
        sb.append("left join t.names as name ");

		if (variables != null && variables.size() > 0) {
			for (int i = 0; i < variables.size(); i++) {
//				sb.append("left join taskVariable as taskVariable" + i + " ");
				sb.append(", TaskVariable as taskVariable" + i + " ");
			}
		}
        
        if (assignee != null) {
        	sb.append(", OrganizationalEntity organizationalEntity ");
        }
        
        if (candidateGroups != null) {
        	sb.append(", OrganizationalEntity potentialOwners ");
        }
        
        return sb.toString();
	}
	
	private String getStatementWhere() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("WHERE 1=1 ");
		
		if (assignee != null) {
        	sb.append("and organizationalEntity.id = :assignee ");
        	if (assigneeType.equals(ASSIGNEE_TYPE.BusinessAdministrator)) {
        		sb.append("and organizationalEntity in elements ( t.peopleAssignments.businessAdministrators  ) ");
        	} else if (assigneeType.equals(ASSIGNEE_TYPE.ExcludedOwner)) {
        		sb.append("and organizationalEntity in elements ( t.peopleAssignments.excludedOwners  ) ");
        	} else if (assigneeType.equals(ASSIGNEE_TYPE.PotentialOwner)) {
        		sb.append("and organizationalEntity in elements ( t.peopleAssignments.potentialOwners  ) ");
        	} else if (assigneeType.equals(ASSIGNEE_TYPE.Recipient)) {
        		sb.append("and organizationalEntity in elements ( t.peopleAssignments.recipients  ) ");
        	}
        }
		
		if (candidateGroups != null) {
			sb.append("and potentialOwners.id in (:candidateGroups) ");
			sb.append("and potentialOwners in elements ( t.peopleAssignments.potentialOwners ) ");
		}
		
		if (actualOwner != null) {
			sb.append("and t.taskData.actualOwner.id = :actualOwner ");
		} else if (unassigned) {
			sb.append("and t.taskData.actualOwner is null ");
		}
		
		// TODO: String locale = (language != null) ? language : "en-UK";
		sb.append("and (name.language = :language or t.names.size = 0) ");
		sb.append("and (subject.language = :language or t.subjects.size = 0) ");
		sb.append("and (description.language = :language or t.descriptions.size = 0) ");
		
		if (processInstanceId != null) {
        	sb.append("and processInstanceId = :processInstanceId ");
        }
		
		if (archived) {
			sb.append("and archived = 1 ");
		} else {
			sb.append("and archived = 0 ");
		}
		
		if (dueDate != null) {
			// TODO: add dueDate
		}
		
		if (status != null && status.size() > 0) {
			sb.append("and t.taskData.status in (:status) ");
		}
		
		if (name != null) {
			sb.append("and name.shortText = :taskName ");
		}
		
		if (activationTime != null) {
			sb.append("and t.taskData.activationTime < :activationTime ");
		}
		if (workItemId != null) {
			sb.append("and t.taskData.workItemId = :workItemId ");
		}
		
		if (variables != null && variables.size() > 0) {
			for (int i = 0; i < variables.size(); i++) {
				sb.append("and taskVariable" + i + ".name = :variableId" + i + " ");
				sb.append("and taskVariable" + i + ".textValue = :variableValue" + i + " ");
				sb.append("and taskVariable" + i + " in elements ( t.taskVariables ) ");
			}
		}
		
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
		// create query
		
		Query query = em.createQuery(sql);
		
		// add parameters to query
		
		if (processInstanceId != null) {
        	query.setParameter("processInstanceId", processInstanceId);
        }
        
        if (assignee != null) {
        	query.setParameter("assignee", assignee);
        }
        
        if (candidateGroups != null) {
        	query.setParameter("candidateGroups", candidateGroups);
        }
        
        if (actualOwner != null) {
			query.setParameter("actualOwner", actualOwner);
		}
        
        // TODO: Do we can remove language?
        String locale = (language != null) ? language : "en-UK";
        if (locale != null) {
			query.setParameter("language", locale);
		}
        
        if (status != null && status.size() > 0) {
			query.setParameter("status", status);
		}
        
        if (activationTime != null) {
			query.setParameter("activationTime", activationTime);
		}
		if (workItemId != null) {
			query.setParameter("workItemId", workItemId);
		}
		
		if (variables != null && variables.size() > 0) {
	    	int index = 0;
	    	for (TaskQueryVariableValue queryVariableValue : variables) {
	    		boolean isProcessVariable = queryVariableValue.isLocal() ? false : true;
	    		
	    		// TODO: use task variable and process variable in SQL
	    		
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
 		// TODO: move this to AbstractQuery
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
 	
	public TaskQuery setPageList(int firstResult, int maxResults) {
		// TODO: move this to AbstractQuery
		return super.setListPage(firstResult, maxResults);
	}
  
  //getters ////////////////////////////////////////////////////////////////

  public String getName() {
    return name;
  }
  public String getNameLike() {
    return nameLike;
  }
  public String getAssignee() {
    return assignee;
  }
  public boolean getUnassigned() {
    return unassigned;
  }
  // TODO: getDelegationState
  /*public DelegationState getDelegationState() {
    return delegationState;
  }*/
  public boolean getNoDelegationState() {
    return noDelegationState;
  }
  // TODO: getDelegationStateString
  /*public String getDelegationStateString() {
    return (delegationState!=null ? delegationState.toString() : null);
  }*/
  public String getCandidateUser() {
    return candidateUser;
  }
  public String getCandidateGroup() {
    return candidateGroup;
  }
  public Long getProcessInstanceId() {
    return processInstanceId;
  }
  public String getExecutionId() {
    return executionId;
  }
  public String getTaskId() {
    return taskId;
  }
  public String getDescription() {
    return description;
  }
  public String getDescriptionLike() {
    return descriptionLike;
  }
  public Integer getPriority() {
    return priority;
  }
  public Date getCreateTime() {
    return createTime;
  }
  public Date getCreateTimeBefore() {
    return createTimeBefore;
  }
  public Date getCreateTimeAfter() {
    return createTimeAfter;
  }
  public String getKey() {
    return key;
  }
  public String getKeyLike() {
    return keyLike;
  }
  public List<TaskQueryVariableValue> getVariables() {
    return variables;
  }
  public String getProcessDefinitionKey() {
    return processDefinitionKey;
  }
  public String getProcessDefinitionId() {
    return processDefinitionId;
  }
  public String getProcessDefinitionName() {
    return processDefinitionName;
  }
  public String getProcessInstanceBusinessKey() {
    return processInstanceBusinessKey;
  }
  public boolean getExcludeSubtasks() {
    return excludeSubtasks;
  }
  public String getLanguage() {
	  return language;
  }
  public boolean isArchived() {
	  return archived;
  }
}
