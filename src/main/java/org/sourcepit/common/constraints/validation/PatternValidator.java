/**
 * Copyright (c) 2014 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class PatternValidator extends AbstractValidator
{
   @Override
   public void validateConstructorArgument(Object target, Constructor<?> constructor, int argIdx,
      Annotation annotation, Object arg)
   {
      if (!isValid(annotation, arg))
      {
         throw new IllegalArgumentException("Argument " + argIdx
            + " of constructor must match the following regular expression: " + getRegularExpression(annotation));
      }
   }

   @Override
   public void validateMethodArgument(Object target, Method method, int argIdx, Annotation annotation, Object arg)
   {
      if (!isValid(annotation, arg))
      {
         throw new IllegalArgumentException("Argument " + argIdx + " of method " + method.getName()
            + " must match the following regular expression: " + getRegularExpression(annotation));
      }
   }


   @Override
   public void validateReturnedValue(Object target, Method method, Annotation annotation, Object value)
   {
      if (!isValid(annotation, value))
      {
         throw new IllegalStateException("Returned value of " + method.getName()
            + " must match the following regular expression: " + getRegularExpression(annotation));
      }
   }

   private static boolean isValid(Annotation annotation, Object value)
   {
      final String regexp = getRegularExpression(annotation);
      final Pattern pattern;
      try
      {
         pattern = Pattern.compile(regexp);
      }
      catch (PatternSyntaxException e)
      {
         throw new IllegalStateException("Invalid regular expression.'" + regexp + "'", e);
      }
      return value == null || pattern.matcher((CharSequence) value).matches();
   }

   private static String getRegularExpression(Annotation annotation)
   {
      return ((org.sourcepit.common.constraints.Pattern) annotation).regexp();
   }

}
