/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.graphdb.index;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} with additional {@link #size()} and {@link #close()}
 * methods on it, used for iterating over index query results. It is first and
 * foremost an {@link Iterator}, but also an {@link Iterable} JUST so that it
 * can be used in a for-each loop. The <code>iterator()</code> method
 * <i>always</i> returns <code>this</code>.
 * 
 * The size is calculated before-hand so that calling it is always fast.
 * 
 * When you're done with the result and haven't reached the end of the
 * iteration {@link #close()} must be called. Results which are looped through
 * entirely closes automatically.
 * 
 * @param <T> the type of items in the Iterator.
 */
public interface IndexHits<T> extends Iterator<T>, Iterable<T>
{
    /**
     * Returns the size of this iterable. The size is given at construction time
     * so that the size is known before-hand so that it's basically just a
     * simple return statement of an integer variable.
     * 
     * @return the size if this iterable.
     */
    int size();

    /**
     * Closes the underlying search result. This method should be called
     * whenever you've got what you wanted from the result and won't use it
     * anymore. It's necessary to call it so that underlying indexes can dispose
     * of allocated resources for this search result.
     * 
     * You can however skip to call this method if you loop through the whole
     * result, then close() will be called automatically. Even if you loop
     * through the entire result and then call this method it will silently
     * ignore any consequtive call (for convenience).
     */
    void close();
    
    /**
     * Returns the first and only item from the result iterator,
     * or {@code null} there was none. If there were more than one item in the
     * result a {@link NoSuchElementException} will be thrown. This method
     * must be called first in the iteration and will grab the first item
     * from the iteration, so the result is considered broken after this call.
     * 
     * @return the first and only item, or {@code null} if none.
     */
    T getSingle();
    
    /**
     * Returns the score of the most recently fetched item from this iterator
     * (from {@link #next()}). The range of the returned values is up to the
     * {@link Index} implementation to dictate. 
     * @return the score of the most recently fetched item from this iterator.
     */
    float currentScore();
}
