package org.jbpm.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Abstract query class that adds methods to query for variable values.
 * 
 * @author Dmitry Farafonov
 * @param <U>
 */
public abstract class AbstractVariableQueryImpl<T> extends AbstractQuery<T>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	protected List<QueryVariableValue> queryVariableValues = new ArrayList<QueryVariableValue>();

	public AbstractVariableQueryImpl(EntityManager em) {
		super(em);
	}

	public AbstractVariableQueryImpl() {
		super();
	}

	@Override
	public abstract long executeCount(EntityManager em);

	@Override
	public abstract List<?> executeList(EntityManager em, Page page);

	@SuppressWarnings("unchecked")
	public T variableValueEquals(String name, Object value) {
		if (value != null && value.getClass().isArray()) {
			variableValueIn(name, value);
		} else {
			addVariable(name, value, QueryOperator.EQUALS, true);
		}
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueEquals(Object value) {
		queryVariableValues.add(new QueryVariableValue(null, value,
				QueryOperator.EQUALS, true));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueIn(String name, Object value) {
		addVariable(name, value, QueryOperator.IN, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueEqualsIgnoreCase(String name, String value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		addVariable(name, value.toLowerCase(),
				QueryOperator.EQUALS_IGNORE_CASE, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueNotEqualsIgnoreCase(String name, String value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		addVariable(name, value.toLowerCase(),
				QueryOperator.NOT_EQUALS_IGNORE_CASE, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueNotEquals(String name, Object value) {
		addVariable(name, value, QueryOperator.NOT_EQUALS, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueGreaterThan(String name, Object value) {
		addVariable(name, value, QueryOperator.GREATER_THAN, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueGreaterThanOrEqual(String name, Object value) {
		addVariable(name, value, QueryOperator.GREATER_THAN_OR_EQUAL, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueLessThan(String name, Object value) {
		addVariable(name, value, QueryOperator.LESS_THAN, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueLessThanOrEqual(String name, Object value) {
		addVariable(name, value, QueryOperator.LESS_THAN_OR_EQUAL, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T variableValueLike(String name, String value) {
		addVariable(name, value, QueryOperator.LIKE, true);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T processVariableValueEquals(String variableName,
			Object variableValue) {
		addVariable(variableName, variableValue, QueryOperator.EQUALS, false);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T processVariableValueEquals(Object value) {
		queryVariableValues.add(new QueryVariableValue(null, value,
				QueryOperator.EQUALS, false));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T processVariableValueNotEquals(String variableName,
			Object variableValue) {
		addVariable(variableName, variableValue, QueryOperator.NOT_EQUALS,
				false);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T processVariableValueEqualsIgnoreCase(String name, String value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		addVariable(name, value.toLowerCase(),
				QueryOperator.EQUALS_IGNORE_CASE, false);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T processVariableValueNotEqualsIgnoreCase(String name, String value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		addVariable(name, value.toLowerCase(),
				QueryOperator.NOT_EQUALS_IGNORE_CASE, false);
		return (T) this;
	}

	private void addVariable(String name, Object value, QueryOperator operator,
			boolean localScope) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		if (value == null || isBoolean(value)) {
			// Null-values and booleans can only be used in EQUALS and
			// NOT_EQUALS
			switch (operator) {
			case GREATER_THAN:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'greater than' condition");
			case LESS_THAN:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'less than' condition");
			case GREATER_THAN_OR_EQUAL:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'greater than or equal' condition");
			case LESS_THAN_OR_EQUAL:
				throw new RuntimeException(
						"Booleans and null cannot be used in 'less than or equal' condition");
			}

			if (operator == QueryOperator.EQUALS_IGNORE_CASE
					&& (value == null || !(value instanceof String))) {
				throw new RuntimeException(
						"Only string values can be used with 'equals ignore case' condition");
			}

			if (operator == QueryOperator.NOT_EQUALS_IGNORE_CASE
					&& (value == null || !(value instanceof String))) {
				throw new RuntimeException(
						"Only string values can be used with 'not equals ignore case' condition");
			}

			if (operator == QueryOperator.LIKE
					&& (value == null || !(value instanceof String))) {
				throw new RuntimeException(
						"Only string values can be used with 'like' condition");
			}
		}
		queryVariableValues.add(new QueryVariableValue(name, value, operator,
				localScope));
	}

	private boolean isBoolean(Object value) {
		if (value == null) {
			return false;
		}
		return Boolean.class.isAssignableFrom(value.getClass())
				|| boolean.class.isAssignableFrom(value.getClass());
	}

	public List<QueryVariableValue> getQueryVariableValues() {
		return queryVariableValues;
	}

}
