/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.sourcepit.common.constraints.validation.AbstractValidator;
import org.sourcepit.common.constraints.validation.NotNullValidator;
import org.sourcepit.common.constraints.validation.PatternValidator;

public aspect MethodAspects
{
   private static final Map<Class<?>, AbstractValidator> CONSTRAINTS = new HashMap<Class<?>, AbstractValidator>();
   static
   {
      CONSTRAINTS.put(NotNull.class, new NotNullValidator());
      CONSTRAINTS.put(Pattern.class, new PatternValidator());
   }

   pointcut constraintedConstructorCall() : execution(* .new(..,@org.sourcepit.common.constraints.* (*),..));

   pointcut constraintedMethodArgs() : execution(* *(..,@org.sourcepit.common.constraints.* (*),..));

   pointcut constraintedMethodReturnValue() : execution(@org.sourcepit.common.constraints.* !void *(..));

   before() : constraintedConstructorCall() {
      final Object target = thisJoinPoint.getTarget();
      final Constructor<?> constructor = ((ConstructorSignature) thisJoinPoint.getSignature()).getConstructor();
      final Annotation[][] parametersAnnotations = constructor.getParameterAnnotations();
      final Object[] args = thisJoinPoint.getArgs();
      final int offset = args.length - parametersAnnotations.length;
      for (int i = offset; i < args.length; i++)
      {
         final Annotation[] parameterAnnotations = parametersAnnotations[i - offset];
         if (parameterAnnotations.length > 0)
         {
            final Object arg = args[i];
            for (Annotation annotation : parameterAnnotations)
            {
               final AbstractValidator constraint = getConstraint(annotation);
               if (constraint != null)
               {
                  constraint.validateConstructorArgument(target, constructor, i, annotation, arg);
               }
            }
         }
      }
   }

   before() : constraintedMethodArgs() {
      final Object target = thisJoinPoint.getTarget();
      final Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
      final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
      final Object[] args = thisJoinPoint.getArgs();
      for (int i = 0; i < args.length; i++)
      {
         final Annotation[] parameterAnnotations = parametersAnnotations[i];
         if (parameterAnnotations.length > 0)
         {
            final Object arg = args[i];
            for (Annotation annotation : parameterAnnotations)
            {
               final AbstractValidator constraint = getConstraint(annotation);
               if (constraint != null)
               {
                  constraint.validateMethodArgument(target, method, i, annotation, arg);
               }
            }
         }
      }
   }

   after() returning(Object returnedValue): constraintedMethodReturnValue()
   {
      final Object target = thisJoinPoint.getTarget();
      final Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
      final Annotation[] annotations = method.getAnnotations();
      if (annotations.length > 0)
      {
         for (Annotation annotation : annotations)
         {
            final AbstractValidator constraint = getConstraint(annotation);
            if (constraint != null)
            {
               constraint.validateReturnedValue(target, method, annotation, returnedValue);
            }
         }
      }
   }

   private AbstractValidator getConstraint(Annotation annotation)
   {
      return CONSTRAINTS.get(annotation.annotationType());
   }
}
