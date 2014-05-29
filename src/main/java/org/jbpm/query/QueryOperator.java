package org.jbpm.query;


/**
 * Used to indicate the operator that should be used to comparing values in a query clause.
 * 
 * @author Frederik Heremans
 */
public enum QueryOperator {
  EQUALS,
  NOT_EQUALS,
  GREATER_THAN,
  GREATER_THAN_OR_EQUAL,
  LESS_THAN,
  LESS_THAN_OR_EQUAL,
  LIKE,
  EQUALS_IGNORE_CASE,
  NOT_EQUALS_IGNORE_CASE,
  IN,
}
