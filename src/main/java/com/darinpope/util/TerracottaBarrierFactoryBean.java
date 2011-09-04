package com.darinpope.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.terracotta.api.ClusteringToolkitExtension;
import org.terracotta.coordination.Barrier;

public class TerracottaBarrierFactoryBean implements InitializingBean, FactoryBean, DisposableBean {

  @Autowired
  private ClusteringToolkitExtension toolkit;

  private String name;

  private Integer parties;

  @Required
  public void setName(String name) {
    this.name = name;
  }

  @Required
  public void setParties(Integer parties) {
    this.parties = parties;
  }

  private Barrier barrier;

  public Object getObject() throws Exception {
    return barrier;
  }

  public Class<?> getObjectType() {
    return (barrier != null ? barrier.getClass() : Barrier.class);
  }

  public boolean isSingleton() {
    return true;
  }

  public void afterPropertiesSet() throws Exception {
    barrier =  toolkit.getBarrier(name,parties);
  }

  public void destroy() throws Exception {
    if(toolkit != null && toolkit.getClusterInfo() != null && toolkit.getClusterInfo().areOperationsEnabled()) {
      toolkit.unregisterBarrier(name);
    }
  }
}
