/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import javax.validation.constraints.NotNull;

import org.junit.Test;

public class MethodAspectsTest
{
   @Test
   public void testConstraintedMethodArgs()
   {
      try
      {
         methodNotNullArg(null);
      }
      catch (IllegalArgumentException e)
      {
      }

      methodNotNullArg("");

      try
      {
         methodNotNullArgs(null, null);
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         methodNotNullArgs("", null);
      }
      catch (IllegalArgumentException e)
      {
      }

      methodNotNullArgs("", "");
   }

   @Test
   public void testConstraintedMethodReturnValue()
   {
      try
      {
         methodReturnNotNull(null);
      }
      catch (IllegalStateException e)
      {
      }
      
      methodReturnNotNull("");
   }

   private void methodNotNullArg(@NotNull String arg0)
   {
   }

   private void methodNotNullArgs(@NotNull String arg0, @NotNull String arg1)
   {
   }

   @NotNull
   private String methodReturnNotNull(String returnValue)
   {
      return returnValue;
   }

}
