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

public class PagingUtil
{
    public static final int DEFAULT_PAGE_SIZE = 50;

    private int currentPage;

    private int pageSize;

    private int total;

    private String link;
   
    public PagingUtil()
    {
    }

    public PagingUtil( String link, int pageSize )
    {
        currentPage = 1;
        this.pageSize = pageSize;
        total = 0;
        this.link = link;
    }
    
  //New Requirement #1636 User is able to see and dispense drugs in patient queue for issuing drugs, as ordered from dashboard
    public PagingUtil(Integer pageSize, Integer currentPage, int total) {
		this.pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
		this.total = total;
		this.currentPage = currentPage == null || currentPage > total ? 1
				: currentPage;
	}
    
    public PagingUtil( String link, Integer pageSize, Integer currentPage, int total )
    {
        this.pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        this.total = total;
        this.currentPage =  currentPage == null || currentPage > total   ? 1 : currentPage;
        this.link = link;
    }

    public String getBaseLink()
    {
        if ( link.indexOf( "?" ) < 0 )
            return (new StringBuilder( String.valueOf( link ) )).append( "?" ).toString();
        else
            return (new StringBuilder( String.valueOf( link ) )).append( "&" ).toString();
    }

    public int getNumberOfPages()
    {
        if ( total % pageSize == 0 )
            return total / pageSize;
        else
            return total / pageSize + 1;
    }

    public int getStartPage()
    {
        int startPage = 1;
        if ( currentPage > 2 )
        {
            startPage = currentPage - 2;
            if ( getNumberOfPages() - startPage < 4 )
            {
                startPage = getNumberOfPages() - 4;
                if ( startPage <= 0 )
                    startPage = 1;
            }
        }
        return startPage;
    }

    public int getStartPos()
    {
        return currentPage <= 0 ? 0 : (currentPage - 1) * pageSize;
    }

    public int getEndPos()
    {
        int endPos = (getStartPos() + getPageSize()) - 1;
        endPos = endPos >= getTotal() ? getTotal() - 1 : endPos;
        return endPos;
    }

    public int getCurrentPage()
    {
        if ( currentPage > total )
            currentPage = total;
        return currentPage;
    }

    public void setCurrentPage( int currentPage )
    {
        if ( currentPage > 0 )
            this.currentPage = currentPage;
        else
            this.currentPage = 1;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize( int pageSize )
    {
        if ( pageSize > 0 )
            this.pageSize = pageSize;
        else
            this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal( int total )
    {
        this.total = total;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink( String link )
    {
        this.link = link;
    }
    
    //New Requirement #1636 User is able to see and dispense drugs in patient queue for issuing drugs, as ordered from dashboard
    public int getPrev() {
		if (currentPage - 1 > 1)
			return currentPage - 1;
		else
			return 1;
	}

	public int getNext() {
		if (currentPage + 1 < getNumberOfPages()) {
			return currentPage + 1;
		} else {
			return getNumberOfPages();
		}
	}

}

