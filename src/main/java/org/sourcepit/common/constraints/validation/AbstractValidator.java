/**
 * Copyright (c) 2014 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public abstract class AbstractValidator
{
   public abstract void validateConstructorArgument(Object target, Constructor<?> constructor, int i,
      Annotation annotation, Object arg);

   public abstract void validateMethodArgument(Object target, Method method, int argIdx, Annotation annotation, Object arg);

   public abstract void validateReturnedValue(Object target, Method method, Annotation annotation, Object value);

}
