/**
 * Copyright (c) 2014 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
