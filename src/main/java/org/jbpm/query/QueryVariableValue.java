package org.jbpm.query;

import java.io.Serializable;

public class QueryVariableValue implements Serializable {
	private static final long serialVersionUID = 1L;
	  private String name;
	  private Object value;
	  private QueryOperator operator;
	  
	  //private VariableInstanceEntity variableInstanceEntity;
	  private boolean local;
	    
	  public QueryVariableValue(String name, Object value, QueryOperator operator, boolean local) {
	    this.name = name;
	    this.value = value;
	    this.operator = operator;
	    this.local = local;
	  }
	  
	  public String getName() {
	    return name;
	  }
	  
	  public String getOperator() {
	    if(operator != null) {
	      return operator.toString();      
	    }
	    return QueryOperator.EQUALS.toString();
	  }
	  
	  public Object getValue() {
	    return value;
	  }
	  
	  public boolean isLocal() {
	    return local;
	  }
}
