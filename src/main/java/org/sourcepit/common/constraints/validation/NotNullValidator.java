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

package org.sourcepit.common.constraints.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class NotNullValidator extends AbstractValidator
{
   @Override
   public void validateConstructorArgument(Object target, Constructor<?> constructor, int argIdx,
      Annotation annotation, Object arg)
   {
      if (arg == null)
      {
         throw new IllegalArgumentException("Argument " + argIdx + " of constructor must not be null.");
      }
   }

   @Override
   public void validateMethodArgument(Object target, Method method, int argIdx, Annotation annotation, Object arg)
   {
      if (arg == null)
      {
         throw new IllegalArgumentException("Argument " + argIdx + " of method " + method.getName()
            + " must not be null.");
      }
   }

   @Override
   public void validateReturnedValue(Object target, Method method, Annotation annotation, Object value)
   {
      if (value == null)
      {
         throw new IllegalStateException("Method " + method.getName() + " unexpectedly returned null.");
      }
   }
}
