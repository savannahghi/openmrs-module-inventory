/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.ehrinventory.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestUtil
{
    public RequestUtil()
    {
    }

    public static String getCurrentLink( HttpServletRequest request )
    {
        //return request.getServletPath();
    	
    	return request.getRequestURI();
        
    }

    public static String getPathInfo( HttpServletRequest request )
    {
        return request.getPathInfo().substring( 1 );
    }

    public static String getSessionString( HttpServletRequest request, String attributeName )
    {
        HttpSession session = request.getSession();
        if ( session.getAttribute( attributeName ) == null )
            return null;
        else
            return session.getAttribute( attributeName ).toString();
    }

    public static Object getSessionObject( HttpServletRequest request, String attributeName, Class<?> clazz )
    {
        HttpSession session = request.getSession();
        if ( session != null )
        {
            Object object = session.getAttribute( attributeName );
            if ( object != null )
                try
                {
                    return object;
                }
                catch ( Exception exception )
                {
                }
        }
        return null;
    }

    public static void removeAttribute( HttpServletRequest request, String attributeName )
    {
        HttpSession session = request.getSession( false );
        if ( session != null )
            session.removeAttribute( attributeName );
    }

    public static String getSessionStringAndRemove( HttpServletRequest request, String attributeName )
    {
        String value = getSessionString( request, attributeName );
        removeAttribute( request, attributeName );
        return value;
    }

}
