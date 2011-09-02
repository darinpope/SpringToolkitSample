package com.darinpope.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.terracotta.api.ClusteringToolkit;
import org.terracotta.api.TerracottaClient;

public class Clustering implements InitializingBean, FactoryBean, DisposableBean {

  private String terracottaConfigUrl;

  @Required
  public void setTerracottaConfigUrl(String terracottaConfigUrl) {
    this.terracottaConfigUrl = terracottaConfigUrl;
  }

  private ClusteringToolkit clusteringToolkit;

  private TerracottaClient terracottaClient;

  public Object getObject() throws Exception {
    return clusteringToolkit;
  }

  public Class<?> getObjectType() {
    return (clusteringToolkit != null ? clusteringToolkit.getClass() : ClusteringToolkit.class);
  }

  public boolean isSingleton() {
    return true;
  }

  public void afterPropertiesSet() throws Exception {
    terracottaClient =  new TerracottaClient(terracottaConfigUrl);
    clusteringToolkit = terracottaClient.getToolkit();
  }

  public void destroy() throws Exception {
    if(terracottaClient != null) {
      terracottaClient.shutdown();
    }
  }
}