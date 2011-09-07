package com.darinpope.util;


import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTerracottaBlockingQueue {
  private static final Logger logger = Logger.getLogger(TestTerracottaBlockingQueue.class.getName());

  @Resource(name="thisIsTheTestRequestQueue")
  private BlockingQueue<byte[]> queue;

  @Test
  public void putToQueue() {
    try {
      queue.put(SerializationUtils.serialize("addToQueue"));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void takeFromQueue() {
    try {
      String fi = (String) SerializationUtils.deserialize(queue.take());
      assertEquals("addToQueue wasn't on the queue","addToQueue",fi);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

}
