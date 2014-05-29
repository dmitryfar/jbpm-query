package org.jbpm.query;

import java.util.HashMap;
import java.util.Map;


/**
 * Contains the possible properties which can be used in a {@link ProcessInstanceQueryProperty}.
 * 
 * @author Joram Barrez
 * @author Dmitry Farafonov
 */
public class ProcessInstanceQueryProperty implements QueryProperty {
  
  private static final long serialVersionUID = 1L;

  private static final Map<String, ProcessInstanceQueryProperty> properties = new HashMap<String, ProcessInstanceQueryProperty>();

  public static final ProcessInstanceQueryProperty PROCESS_INSTANCE_ID_ = new ProcessInstanceQueryProperty("processInstanceId");
  public static final ProcessInstanceQueryProperty PROCESS_DEFINITION_ID = new ProcessInstanceQueryProperty("processId");
  public static final ProcessInstanceQueryProperty START_TIME = new ProcessInstanceQueryProperty("start");
  public static final ProcessInstanceQueryProperty END_TIME = new ProcessInstanceQueryProperty("end");
  
  private String name;

  public ProcessInstanceQueryProperty(String name) {
    this.name = name;
    properties.put(name, this);
  }

  public String getName() {
    return name;
  }
  
  public static ProcessInstanceQueryProperty findByName(String propertyName) {
    return properties.get(propertyName);
  }

}

