/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.common.constraints;

import static org.junit.Assert.fail;

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

      IFoo iFoo = new IFoo()
      {
         @Override
         public Object foo(@NotNull Object o)
         {
            return null;
         }
      };

      try
      {
         iFoo.foo(null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      iFoo.foo("");
   }

   private interface IFoo
   {
      Object foo(Object o);
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

      IFoo iFoo = new IFoo()
      {
         @Override
         @NotNull
         public Object foo(Object o)
         {
            return o;
         }
      };

      try
      {
         iFoo.foo(null);
         fail();
      }
      catch (IllegalStateException e)
      {
      }

      iFoo.foo("");
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

      try
      {
         new Foo3(null, null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         new Foo3(null, "");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      new Foo3("", null);

      new Foo3("", "");
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
      Foo(@NotNull String arg0, @NotNull String arg1)
      {
      }
   }

   private static class Foo2
   {
      Foo2(String arg0, @NotNull String arg1)
      {
      }
   }

   private class Foo3
   {
      Foo3(@NotNull String arg0, String arg1)
      {
      }
   }

}
