jbpm-query
==========

Feature to make custom queries in jbpm.


## ProcessInstanceQuery

### Create query
```java
ProcessInstanceQuery query = new ProcessInstanceQueryImpl();
query.processDefinitionId(definitionId);
query.finished();
query.setPageList(start, end);
```
or simple
```java
ProcessInstanceQuery query = new ProcessInstanceQueryImpl()
	.variableValueEquals("companyId", companyId)
	.variableValueEquals("userId", userId)
	.processInstanceIds(longSet)
	.unfinished();
```

### How to use it

You need to use `EntityManager`

```java
public static long findProcessInstancesCount(ProcessInstanceQuery processInstanceQuery) {
	EntityManager em = getEntityManager();
	boolean newTx = joinTransaction(em);
	
	// ProcessInstanceQuery
	ProcessInstanceQueryImpl piQueryImpl = (ProcessInstanceQueryImpl) processInstanceQuery;
	
	long count = (Long) piQueryImpl.count(em);
	
	closeEntityManager(em, newTx);
	return count;
}
```

## TaskQuery

### Create query
```java
TaskQuery query = new TaskQueryImpl()
	.actualOwner("iam")
	.processInstanceId(101)
	.taskVariableValueEquals("companyId", 10156)
	.taskVariableValueNotEqualsIgnoreCase("manager", "dmitry far")
	.taskCompleted(true)
	.setPageList(50, 100);
```

### How to use it

After that in TaskPersistenceManager, for example, you can retrieve task list:
```java
public List<Task> getTasks(TaskQuery taskQuery) {
	List<Task> result = new ArrayList<Task>();
	...
	try {
		// beginTransaction
		
		// TaskQuery
		TaskQueryImpl taskQueryImpl = (TaskQueryImpl) taskQuery;
		
		result = (List<Task>) taskQueryImpl.list(em);
		
		// endTransaction
	} catch(Exception e) { ... }
	return result;
}
```

## P.S.
To use variables in queries you have to add tables to store tasks- and process variables.
