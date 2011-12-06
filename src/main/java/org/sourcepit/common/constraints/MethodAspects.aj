/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.aspectj.lang.reflect.MethodSignature;

public aspect MethodAspects
{
   private static final IValidator VALIDATOR = ValidatorFacade.getInstance();

   pointcut constraintedMethodArgs() : execution(* *(..,@javax.validation.constraints.* (*),..));

   pointcut constraintedMethodReturnValue() : execution(@javax.validation.constraints.* !void *(..));

   @SuppressWarnings({ "rawtypes", "unchecked" })
   before() : constraintedMethodArgs() {
      final MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
      final Class clazz = methodSignature.getDeclaringType();
      final Method method = methodSignature.getMethod();
      final Object[] args = thisJoinPoint.getArgs();
      for (int i = 0; i < args.length; i++)
      {
         final Object arg = args[i];

         final Set<ConstraintViolation<?>> violations = VALIDATOR.validateArgument(clazz, method, i, arg);
         if (!violations.isEmpty())
         {
            ConstraintViolation<?> firstViolation = violations.iterator().next();
            throw new ConstraintViolationException("Argument " + i + " " + firstViolation.getMessage(), violations);
         }
      }
   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
   after() returning(Object returnedValue): constraintedMethodReturnValue()
   {
      final MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
      final Class clazz = methodSignature.getDeclaringType();
      final Method method = methodSignature.getMethod();
      final Set<ConstraintViolation<?>> violations = VALIDATOR.validateReturnedValue(clazz, method, returnedValue);
      if (!violations.isEmpty())
      {
         ConstraintViolation<?> firstViolation = violations.iterator().next();
         throw new ConstraintViolationException("Returned value " + firstViolation.getMessage(), violations);
      }
   }
}
