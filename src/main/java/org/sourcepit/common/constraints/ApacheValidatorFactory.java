/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.common.constraints;

import javax.validation.spi.ConfigurationState;

import org.apache.bval.jsr303.xml.ValidationMappingParser;

/**
 * ApacheValidatorFactory
 * 
 * @author Bernd
 */
public class ApacheValidatorFactory extends org.apache.bval.jsr303.ApacheValidatorFactory
{

   /**
    * @param configurationState
    */
   public ApacheValidatorFactory(ConfigurationState configurationState)
   {
      super(configurationState);
   }

   @Override
   protected void configure(ConfigurationState configuration)
   {
      getProperties().putAll(configuration.getProperties());
      setMessageInterpolator(configuration.getMessageInterpolator());
      setTraversableResolver(configuration.getTraversableResolver());
      setConstraintValidatorFactory(configuration.getConstraintValidatorFactory());
      if (!configuration.isIgnoreXmlConfiguration())
      {
         ValidationMappingParser parser = new ValidationMappingParser(this);
         parser.processMappingConfig(configuration.getMappingStreams());
      }
   }

}
