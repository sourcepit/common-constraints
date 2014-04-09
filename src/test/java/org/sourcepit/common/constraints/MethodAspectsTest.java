/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import static org.junit.Assert.*;

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
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      methodNotNullArg("");

      try
      {
         methodNotNullArgs(null, null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         methodNotNullArgs("", null);
         fail();
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
         fail();
      }
      catch (IllegalStateException e)
      {
      }

      methodReturnNotNull("");
   }

   @Test
   public void testConstraintedConstructorCall() throws Exception
   {
      try
      {
         new Foo(null, null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
      try
      {
         new Foo(null, "");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         new Foo("", null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      new Foo("", "");

      try
      {
         new Foo2(null, null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
      
      new Foo2(null, "");

      try
      {
         new Foo2("", null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      new Foo2("", "");
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

   private static class Foo
   {
      Foo(@NotNull String arg0, @NotNull String arg2)
      {
      }
   }

   private static class Foo2
   {
      Foo2(String arg0, @NotNull String arg2)
      {
      }
   }

}
