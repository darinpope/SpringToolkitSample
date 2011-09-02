package com.darinpope.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terracotta.util.ClusteredAtomicLong;

import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTerracottaAtomicLong {
  private static final Logger logger = Logger.getLogger(TestTerracottaAtomicLong.class.getName());

  @Autowired
  @Qualifier("testTerracottaAtomicLong")
  private ClusteredAtomicLong terracottaAtomicLong;

  @Test
  public void terracottaAtomicLong() {
    long testValue = terracottaAtomicLong.getAndIncrement();
    assertEquals("Value is not 0",new Long(0).longValue(),testValue);
    testValue = terracottaAtomicLong.getAndIncrement();
    assertEquals("Value is not 1",new Long(1).longValue(),testValue);
    testValue = terracottaAtomicLong.get();
    assertEquals("Value is not 2",new Long(2).longValue(),testValue);
    testValue = terracottaAtomicLong.decrementAndGet();
    assertEquals("Value is not 1",new Long(1).longValue(),testValue);

  }
}
