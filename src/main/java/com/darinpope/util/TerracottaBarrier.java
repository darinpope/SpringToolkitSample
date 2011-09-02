package com.darinpope.util;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.terracotta.api.ClusteringToolkit;
import org.terracotta.coordination.Barrier;

public class TerracottaBarrier implements InitializingBean, FactoryBean {

  @Autowired
  private ClusteringToolkit clustering;

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
    barrier =  clustering.getBarrier(name,parties);
  }
}
