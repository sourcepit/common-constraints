/**
 * Copyright (c) 2014 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import static org.junit.Assert.fail;

import org.junit.Test;

public class PatternTest
{

   @Test
   public void test()
   {
      constraintedMethod(null);

      try
      {
         constraintedMethod("");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
      
      constraintedMethod("2014-04-10");
   }

   private void constraintedMethod(@Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String value)
   {
   }

}
