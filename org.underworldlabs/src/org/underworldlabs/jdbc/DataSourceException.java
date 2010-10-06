/*
 * DataSourceException.java
 *
 * Copyright (C) 2002-2007 Takis Diakoumis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.underworldlabs.jdbc;

import java.sql.SQLException;

/**
 * Generic exception thrown by data source related methods/classes.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 917 $
 * @date     $Date: 2007-06-17 22:47:42 +1000 (Sun, 17 Jun 2007) $
 */
public class DataSourceException extends Exception {
    
    /** closed connection indictaor value */
    private boolean connectionClosed;
    
    /** underlying dump cause */
    private Throwable cause;
    
    public DataSourceException() {
        super();
    }
    
    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, boolean connectionClosed) {
        super(message);
        this.connectionClosed = connectionClosed;
    }

    public DataSourceException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }
    
    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public Throwable getCause() {
        return cause;
    }
    
    public boolean wasConnectionClosed() {
        return connectionClosed;
    }
    
    public String getExtendedMessage() {
        if (cause == null) {
            return getMessage() == null ? "" : getMessage();
        }

        StringBuffer sb = new StringBuffer();
        String message = cause.getMessage();
        if (message != null) {
            sb.append(message);
        } else {
            sb.append(cause);
        }

        if (cause instanceof SQLException) {            
            SQLException sqlCause = (SQLException)cause;
            sb.append("\nError Code: " + sqlCause.getErrorCode());

            String state = sqlCause.getSQLState();
            if (state != null) {
                sb.append("\nSQL State Code: " + state);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}




