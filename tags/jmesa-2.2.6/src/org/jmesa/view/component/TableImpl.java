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
package org.jmesa.view.component;

import org.apache.commons.lang.StringUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.renderer.TableRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class TableImpl extends AbstractContextSupport implements Table {
    private Row row;
    private String caption;
    private TableRenderer tableRenderer;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setCaptionKey(String key) {
        if (StringUtils.isNotEmpty(key)) {
            this.caption = getCoreContext().getMessage(key);
        }
    }

    /**
     * Use the setCaptionKey instead. This makes it consistent with the tag library.
     */
    @Deprecated
    public void setCaption(String caption, boolean message) {
        if (message) {
            this.caption = getCoreContext().getMessage(caption);
        } else {
            this.caption = caption;
        }
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public TableRenderer getTableRenderer() {
        return tableRenderer;
    }

    public void setTableRenderer(TableRenderer tableRenderer) {
        this.tableRenderer = tableRenderer;
        SupportUtils.setWebContext(tableRenderer, getWebContext());
        SupportUtils.setCoreContext(tableRenderer, getCoreContext());
        tableRenderer.setTable(this);
    }
}
