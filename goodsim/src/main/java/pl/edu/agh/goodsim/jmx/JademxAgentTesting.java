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

   private JadeMXServer jadeMXServer = null;
   private JadeRuntime jadeRuntime = null;
   JadePlatform platformMBean = null;
   private static final String JADEMX_CONFIGURATION_XML =
      "jademx-configuration.xml";

   protected void setUp() throws Exception {
      jadeMXServer = JadeMXServerFactory.jadeMXServerBySysProp();
      JadeFactory jadeFactory = new JadeFactory(jadeMXServer);
      jadeRuntime = (JadeRuntime) jadeFactory.runtimeInstance();
      JadePlatform platformMBeans[] = jadeRuntime.platformsFromConfigResource(JADEMX_CONFIGURATION_XML);
      platformMBean = platformMBeans[0];
   }


   protected void tearDown() throws Exception {
      if (null != jadeRuntime) {
         jadeRuntime.shutdown();
      }
   }

   public static void main(String[] args) throws Exception {
      JademxAgentTesting testing = new JademxAgentTesting();
      testing.setUp();
      System.out.println("Waiting forever...");
      Thread.sleep(Long.MAX_VALUE);
      testing.tearDown();
   }
}
