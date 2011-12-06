/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;


public interface IValidator
{

   <T> Set<ConstraintViolation<T>> validateArgument(Class<T> clazz, Method method, int argIndex, Object argValue);

   <T> Set<ConstraintViolation<T>> validateReturnedValue(Class<T> clazz, Method method, Object returnedValue);

}