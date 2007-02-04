/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmesaweb.service;

import java.util.List;

import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.dao.PresidentDao;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class PresidentServiceImpl implements PresidentService {
    private PresidentDao presidentDao;

    public List getPresidents() {
        return presidentDao.getPresidents();
    }

    public int getPresidentsCountWithFilter(PresidentFilter filter) {
        return presidentDao.getPresidentsCountWithFilter(filter);
    }

    public List getPresidentsWithFilterAndSort(PresidentFilter filter, PresidentSort sort, int rowStart, int rowEnd) {
        return presidentDao.getPresidentsWithFilterAndSort(filter, sort, rowStart, rowEnd);
    }

    public void setPresidentDao(PresidentDao presidentsDao) {
        this.presidentDao = presidentsDao;
    }
}
