package org.jbpm.query;

import java.io.Serializable;


/**
 * Describes a property that can be used in a Query.
 * 
 * @author Frederik Heremans
 */
public interface QueryProperty extends Serializable {

  String getName();
}
