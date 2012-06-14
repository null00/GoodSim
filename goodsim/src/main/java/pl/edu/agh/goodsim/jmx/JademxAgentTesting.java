/**
 * Copyright (C) 2012 Sabre Polska, All rights reserved.
 *
 * This software is the confidential and proprietary product of Sabre Polska.
 * Any unauthorized use, reproduction, or transfer of this software is
 * strictly prohibited.
 *
 */
package pl.edu.agh.goodsim.jmx;

import jade.jademx.mbean.JadeFactory;
import jade.jademx.mbean.JadePlatform;
import jade.jademx.mbean.JadeRuntime;
import jade.jademx.server.JadeMXServer;
import jade.jademx.server.JadeMXServerFactory;

/**
 * @author Krzysztof Mycek
 */
public class JademxAgentTesting {

   ///** test(suite) name */
   //private static String name = 
   //    JadeMXSuiteTest.nameWithClass( 
   //            JademxAgentTest.class, 
   //    		"testing JadeAgent: JADE agent MBean class");


   /**
    * jadeMXServer we're using
    */
   private JadeMXServer jadeMXServer = null;

   /**
    * runtime for test use
    */
   private JadeRuntime jadeRuntime = null;

   /**
    * mbean for platform under test
    */
   JadePlatform platformMBean = null;

   /**
    * resource name for configuration of one nop agent
    */
   private static final String ONE_BASE_CONFIG_RESOURCE =
      "pl/edu/agh/goodsim/one-base-agent.xml";

   /**
    * name for dummy agent
    */
   private static final String AGENT_LOCAL_NAME_DUMMY = "dummy";


   // tests


   /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
   protected void setUp() throws Exception {
      // first get JadeMXServer
      jadeMXServer = JadeMXServerFactory.jadeMXServerBySysProp();
      // now get a factory to use
      JadeFactory jadeFactory =
         new JadeFactory(jadeMXServer);
      jadeRuntime = (JadeRuntime) jadeFactory.runtimeInstance();
      //JadeMXSuiteTest.listMBeans(mBeanServer, "at end of setUp()");
      JadePlatform platformMBeans[] =
         jadeRuntime.platformsFromConfigResource(
            ONE_BASE_CONFIG_RESOURCE);
      platformMBean = platformMBeans[0];
   }


   /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
   protected void tearDown() throws Exception {
      //JadeMXSuiteTest.listMBeans(mBeanServer,
      //"at start of JadePlatformTest.tearDown()");
      if (null != jadeRuntime) {
         jadeRuntime.shutdown();
      }
      //JadeMXSuiteTest.listMBeans(mBeanServer,
      //"at end of JadePlatformTest.tearDown()");
   }

   public static void main(String[] args) throws Exception {
      JademxAgentTesting testing = new JademxAgentTesting();
      testing.setUp();
      System.out.println("Waiting forever...");
      Thread.sleep(Long.MAX_VALUE);
      testing.tearDown();
   }
}
