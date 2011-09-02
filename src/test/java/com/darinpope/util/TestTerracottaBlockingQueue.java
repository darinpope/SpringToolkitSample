package com.darinpope.util;


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
      queue.put(serializeMyObject("addToQueue"));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void takeFromQueue() {
    try {
      String fi = deserializeMyObject(queue.take());
      assertEquals("addToQueue wasn't on the queue","addToQueue",fi);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  private byte[] serializeMyObject(String mo) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(mo);
    return baos.toByteArray();
  }

  private String deserializeMyObject(byte[] take) {
    try {
      return (String) new ObjectInputStream(new ByteArrayInputStream(
          take)).readObject();
    } catch (IOException e) {
      throw new AssertionError(e);
    } catch (ClassNotFoundException e) {
      throw new AssertionError(e);
    }
  }

}
