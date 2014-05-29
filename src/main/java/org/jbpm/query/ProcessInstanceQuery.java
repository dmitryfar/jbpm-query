package org.jbpm.query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Allows programmatic querying of {@link ProcessInstance}s.
 * 
 * @author Dmitry Farafonov
 */
public interface ProcessInstanceQuery{

  /** Only select historic process instances with the given process instance.
   * {@link ProcessInstance) ids and {@link ProcessInstance} ids match. */
  ProcessInstanceQuery processInstanceId(Long processInstanceId);

  /** Only select historic process instances whose id is in the given set of ids.
   * {@link ProcessInstance) ids and {@link ProcessInstance} ids match. */
  ProcessInstanceQuery processInstanceIds(Set<Long> processInstanceIds);

  /** Only select historic process instances for the given process definition */
  ProcessInstanceQuery processDefinitionId(String processDefinitionId);

  /** Only select historic process instances that are defined by a process
   * definition with the given key.  */
  ProcessInstanceQuery processDefinitionKey(String processDefinitionKey);

  /** Only select historic process instances that don't have a process-definition of which the key is present in the given list */
  ProcessInstanceQuery processDefinitionKeyNotIn(List<String> processDefinitionKeys);

  /** Only select historic process instances with the given business key */
  ProcessInstanceQuery processInstanceBusinessKey(String processInstanceBusinessKey);

  /** Only select historic process instances that are completely finished. */
  ProcessInstanceQuery finished();

  /** Only select historic process instance that are not yet finished. */
  ProcessInstanceQuery unfinished();

  /** Only select process instances which had a global variable with the given value
   * when they ended. The type only applies to already ended
   * process instances, otherwise use a {@link ProcessInstanceQuery} instead! of
   * variable is determined based on the value, using types configured in
   * {@link ProcessEngineConfiguration#getVariableTypes()}. Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. 
   * @param name of the variable, cannot be null. */
  ProcessInstanceQuery variableValueEquals(String name, Object value);
  
  /** Only select process instances which had at least one global variable with the given value
   * when they ended. The type only applies to already ended
   * process instances, otherwise use a {@link ProcessInstanceQuery} instead! of
   * variable is determined based on the value, using types configured in
   * {@link ProcessEngineConfiguration#getVariableTypes()}. Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. 
   */
  ProcessInstanceQuery variableValueEquals(Object value);
  
  /** 
   * Only select historic process instances which have a local string variable with the 
   * given value, case insensitive.
   * @param name name of the variable, cannot be null.
   * @param value value of the variable, cannot be null.
   */
  ProcessInstanceQuery variableValueEqualsIgnoreCase(String name, String value);

  /** Only select process instances which had a global variable with the given name, but
   * with a different value than the passed value when they ended. Only select
   * process instances which have a variable value greater than the passed
   * value. Byte-arrays and {@link Serializable} objects (which are not
   * primitive type wrappers) are not supported.
   * @param name of the variable, cannot be null. */
  ProcessInstanceQuery variableValueNotEquals(String name, Object value);

  /** Only select process instances which had a global variable value greater than the
   * passed value when they ended. Booleans, Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. Only select process instances which have a variable value
   * greater than the passed value.
   * @param name cannot be null.
   * @param value cannot be null. */
  ProcessInstanceQuery variableValueGreaterThan(String name, Object value);

  /** Only select process instances which had a global variable value greater than or
   * equal to the passed value when they ended. Booleans, Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. Only applies to already ended process instances, otherwise
   * use a {@link ProcessInstanceQuery} instead!
   * @param name cannot be null.
   * @param value cannot be null. */
  ProcessInstanceQuery variableValueGreaterThanOrEqual(String name, Object value);

  /** Only select process instances which had a global variable value less than the
   * passed value when the ended. Only applies to already ended process
   * instances, otherwise use a {@link ProcessInstanceQuery} instead! Booleans,
   * Byte-arrays and {@link Serializable} objects (which are not primitive type
   * wrappers) are not supported.
   * @param name cannot be null.
   * @param value cannot be null. */
  ProcessInstanceQuery variableValueLessThan(String name, Object value);

  /** Only select process instances which has a global variable value less than or equal
   * to the passed value when they ended. Only applies to already ended process
   * instances, otherwise use a {@link ProcessInstanceQuery} instead! Booleans,
   * Byte-arrays and {@link Serializable} objects (which are not primitive type
   * wrappers) are not supported.
   * @param name cannot be null.
   * @param value cannot be null. */
  ProcessInstanceQuery variableValueLessThanOrEqual(String name, Object value);

  /** Only select process instances which had global variable value like the given value
   * when they ended. Only applies to already ended process instances, otherwise
   * use a {@link ProcessInstanceQuery} instead! This can be used on string
   * variables only.
   * @param name cannot be null.
   * @param value cannot be null. The string can include the
   *          wildcard character '%' to express like-strategy: starts with
   *          (string%), ends with (%string) or contains (%string%). */
  ProcessInstanceQuery variableValueLike(String name, String value);

  /** Only select historic process instances that were started before the given date. */
  ProcessInstanceQuery startedBefore(Date date);
  
  /** Only select historic process instances that were started after the given date. */
  ProcessInstanceQuery startedAfter(Date date);
  
  /** Only select historic process instances that were started before the given date. */
  ProcessInstanceQuery finishedBefore(Date date);
  
  /** Only select historic process instances that were started after the given date. */
  ProcessInstanceQuery finishedAfter(Date date);
  
  /** Only select historic process instance that are started by the given user. */
  ProcessInstanceQuery startedBy(String userId);

//  /** Order by the process instance id (needs to be followed by {@link #asc()} or {@link #desc()}). */
//  ProcessInstanceQuery orderByProcessInstanceId();
//  
//  /** Order by the process definition id (needs to be followed by {@link #asc()} or {@link #desc()}). */
//  ProcessInstanceQuery orderByProcessDefinitionId();
//  
//  /** Order by the business key (needs to be followed by {@link #asc()} or {@link #desc()}). */
//  ProcessInstanceQuery orderByProcessInstanceBusinessKey();
//
//  /** Order by the start time (needs to be followed by {@link #asc()} or {@link #desc()}). */
//  ProcessInstanceQuery orderByProcessInstanceStartTime();
//  
//  /** Order by the end time (needs to be followed by {@link #asc()} or {@link #desc()}). */
//  ProcessInstanceQuery orderByProcessInstanceEndTime();
//  
//  /** Order by the duration of the process instance (needs to be followed by {@link #asc()} or {@link #desc()}). */
//  ProcessInstanceQuery orderByProcessInstanceDuration();
  
  /** Only select historic process instances started by the given process
   * instance. {@link ProcessInstance) ids and {@link ProcessInstance}
   * ids match. */
  ProcessInstanceQuery parentProcessInstanceId(Long parentProcessInstanceId);
  
  ProcessInstanceQuery setPageList(int firstResult, int maxResults);
}
