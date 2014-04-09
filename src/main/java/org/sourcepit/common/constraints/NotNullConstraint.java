/**
 * Copyright (c) 2014 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class NotNullConstraint extends AbstractConstraint
{
   @Override
   public void validateArgument(Object target, Method method, int argIdx, Annotation annotation, Object arg)
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
