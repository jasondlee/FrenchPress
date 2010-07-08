package com.steeplesoft.frenchpress.util.upload;

/*
 * net/balusc/http/multipart/MultipartRequest.java
 *
 * Copyright (C) 2009 BalusC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

/**
 * This class represents a multipart request. It not only abstracts the <code>{@link Part}</code>
 * away, but it also provides direct access to the <code>{@link MultipartMap}</code>, so that one
 * can get the uploaded files out of it.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2009/12/uploading-files-in-servlet-30.html
 */
public class MultipartRequest extends HttpServletRequestWrapper {

    // Vars ---------------------------------------------------------------------------------------

    private MultipartMap multipartMap;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct MultipartRequest based on the given HttpServletRequest.
     * @param request HttpServletRequest to be wrapped into a MultipartRequest.
     * @param location The location to save uploaded files in.
     * @throws IOException If something fails at I/O level.
     * @throws ServletException If something fails at Servlet level.
     */
    public MultipartRequest(HttpServletRequest request, String location)
        throws ServletException, IOException
    {
        super(request);
        this.multipartMap = new MultipartMap(request, location);
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public String getParameter(String name) {
        return multipartMap.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return multipartMap.getParameterValues(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return multipartMap.getParameterNames();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return multipartMap.getParameterMap();
    }

    /**
     * @see MultipartMap#getFile(String)
     */
    public FileHolder getFile(String name) {
        return multipartMap.getFile(name);
    }

}