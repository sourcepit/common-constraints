/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.bootstrap.ProviderSpecificBootstrap;

import org.apache.bval.jsr303.ApacheValidationProvider;
import org.apache.bval.jsr303.ApacheValidatorConfiguration;
import org.apache.bval.jsr303.DefaultMessageInterpolator;
import org.apache.bval.jsr303.extensions.MethodValidator;

public class ValidatorFacade implements IValidator
{
   private final static class InstanceHolder
   {
      final static IValidator FACADE = new ValidatorFacade();
   }

   private final MethodValidator methodValidator;

   public static IValidator getInstance()
   {
      return InstanceHolder.FACADE;
   }

   public ValidatorFacade()
   {
      final ClassLoader ctx = Thread.currentThread().getContextClassLoader();
      try
      {
         Thread.currentThread().setContextClassLoader(ApacheValidationProvider.class.getClassLoader());
         
         ProviderSpecificBootstrap<ApacheValidatorConfiguration> provider = Validation
            .byProvider(ApacheValidationProvider.class);

         ApacheValidatorConfiguration configuration = provider.configure();
         configuration.addProperty(ApacheValidatorConfiguration.Properties.VALIDATOR_FACTORY_CLASSNAME,
            ApacheValidatorFactory.class.getName());
         configuration.ignoreXmlConfiguration();
         configuration.messageInterpolator(new DefaultMessageInterpolator()
         {
            @Override
            public String interpolate(String message, Context context, Locale locale)
            {
               return super.interpolate(message, context, new Locale(""));
            }
         });
         methodValidator = configuration.buildValidatorFactory().getValidator().unwrap(MethodValidator.class);
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(ctx);
      }
   }

   /**
    * {@inheritDoc}
    */
   public <T> Set<ConstraintViolation<T>> validateArgument(Class<T> clazz, Method method, int argIndex, Object argValue)
   {
      return methodValidator.validateParameter(clazz, method, argValue, argIndex);
   }

   public <T> Set<ConstraintViolation<T>> validateReturnedValue(Class<T> clazz, Method method, Object returnedValue)
   {
      return methodValidator.validateReturnedValue(clazz, method, returnedValue);
   }
}
