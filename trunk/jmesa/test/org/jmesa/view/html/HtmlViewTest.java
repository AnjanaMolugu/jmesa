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
package org.jmesa.view.html;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jmesa.core.ColumnSort;
import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextImpl;
import org.jmesa.core.DefaultColumnSort;
import org.jmesa.core.DefaultRowFilter;
import org.jmesa.core.Items;
import org.jmesa.core.ItemsImpl;
import org.jmesa.core.Messages;
import org.jmesa.core.Preferences;
import org.jmesa.core.PresidentsDao;
import org.jmesa.core.PropertiesPreferences;
import org.jmesa.core.RowFilter;
import org.jmesa.core.resource.ResourceBundleMessages;
import org.jmesa.limit.DefaultLimitFactory;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.Column;
import org.jmesa.view.ColumnRenderer;
import org.jmesa.view.ColumnValue;
import org.jmesa.view.DefaultColumn;
import org.jmesa.view.DefaultColumnValue;
import org.jmesa.view.DefaultRow;
import org.jmesa.view.DefaultTable;
import org.jmesa.view.Row;
import org.jmesa.view.Table;
import org.jmesa.view.View;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;


/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlViewTest {
	private static final String ID = "pres";
	private static final int MAX_ROWS = 20;
	private static final int TOTAL_ROWS = 60;
	
	@Test
	public void render() {
		CoreContext coreContext = createCoreContext();
		
		// create the table
		Table table = new DefaultTable();
		table.setTheme("jmesa");
		HtmlTableRenderer tableRenderer = new HtmlTableRenderer(coreContext);
		tableRenderer.setStyleClass("table");
		table.setTableRenderer(tableRenderer);
		
		// create the row
		Row row = new DefaultRow();
		row.setHighlighter(true);
		HtmlRowRenderer rowRenderer = new HtmlRowRenderer(coreContext);
		rowRenderer.setHighlightClass("highlight");
		row.setRowRenderer(rowRenderer);
		table.setRow(row);
		
		// create some reusable objects
		ColumnValue columnValue = new DefaultColumnValue();
		ColumnRenderer columnRenderer = new HtmlColumnRenderer(columnValue, coreContext);
		HtmlHeaderRenderer headerRenderer = new HtmlHeaderRenderer(coreContext);
		headerRenderer.setStyleClass("header");

		// create the columns
		Column firstNameColumn = new DefaultColumn("firstName");
		firstNameColumn.setColumnRenderer(columnRenderer);
		firstNameColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(firstNameColumn);
		
		Column lastNameColumn = new DefaultColumn("lastName");
		lastNameColumn.setColumnRenderer(columnRenderer);
		lastNameColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(lastNameColumn);

		Column termColumn = new DefaultColumn("term");
		termColumn.setColumnRenderer(columnRenderer);
		termColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(termColumn);

		Column careerColumn = new DefaultColumn("career");
		careerColumn.setColumnRenderer(columnRenderer);
		careerColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(careerColumn);

		// create the view
		View view = new HtmlView(table, coreContext);

		Object html = view.render();
		
		assertNotNull(html);
	}
	
	public CoreContext createCoreContext() {
		Preferences preferences = new PropertiesPreferences(null, "/org/jmesa/core/test.properties");
		Messages messages = new ResourceBundleMessages(null, "org.jmesa.core.resource.testResourceBundle", Locale.US);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		LimitFactory limitFactory = new DefaultLimitFactory(ID, webContext);
		Limit limit = limitFactory.createLimit();
		
		RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, TOTAL_ROWS);
		limit.setRowSelect(rowSelect);

		RowFilter rowFilter = new DefaultRowFilter();
		ColumnSort columnSort = new DefaultColumnSort();
		
		Collection data = new PresidentsDao().getPresidents();
		
		Items items = new ItemsImpl(data, limit, rowFilter, columnSort);
		
		CoreContextImpl coreContext = new CoreContextImpl(items, limit, preferences, messages, webContext.getLocale());
		
		return coreContext;
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		return results;
	}
}
