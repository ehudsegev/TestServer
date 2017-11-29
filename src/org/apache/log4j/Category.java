/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Contibutors: Alex Blewitt <Alex.Blewitt@ioshq.com>
//              Markus Oestreicher <oes@zurich.ibm.com>
//              Frank Hoering <fhr@zurich.ibm.com>
//              Nelson Minar <nelson@media.mit.edu>
//              Jim Cakalic <jim_cakalic@na.biomerieux.com>
//              Avy Sharell <asharell@club-internet.fr>
//              Ciaran Treanor <ciaran@xelector.com>
//              Jeff Turner <jeff@socialchange.net.au>
//              Michael Horwitz <MHorwitz@siemens.co.za>
//              Calvin Chan <calvin.chan@hic.gov.au>
//              Aaron Greenhouse <aarong@cs.cmu.edu>
//              Beat Meier <bmeier@infovia.com.ar>
//              Colin Sampaleanu <colinml1@exis.com>

package org.apache.log4j;


import java.util.ResourceBundle;



public class Category {

  /**
     The hierarchy where categories are attached to by default.
  */
  //static
  //public
  //final Hierarchy defaultHierarchy = new Hierarchy(new
  //					   RootCategory(Level.DEBUG));

  /**
     The name of this category.
  */
  protected String   name;

  /**
     The assigned level of this category.  The
     <code>level</code> variable need not be assigned a value in
     which case it is inherited form the hierarchy.  */
  volatile protected Level level;

  /**
     The parent of this category. All categories have at least one
     ancestor which is the root category. */
  volatile protected Category parent;

  /**
     The fully qualified name of the Category class. See also the
     getFQCN method. */
  private static final String FQCN = Category.class.getName();

  protected ResourceBundle resourceBundle;


    public Category()
    {

    }

  /**
     This constructor created a new <code>Category</code> instance and
     sets its name.

     <p>It is intended to be used by sub-classes only. You should not
     create categories directly.

     @param name The name of the category.
  */
  protected
  Category(String name) {
    this.name = name;
  }

    /**
     Return the category name.  */
    public
    final
    String getName() {
        return name;
    }

  /**
     This method creates a new logging event and logs the event
     without further checks.  */
  public
  void forcedLog(String fqcn, Priority level, Object message, Throwable t) {

  }



}
