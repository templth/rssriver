/*
 * Licensed to David Pilato (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Author licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.river.rss;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class RssRiverTtlTest extends AbstractRssRiverSimpleTest {
    private static final int update_rate = 10 * 1000;
	/**
	 * Overriding mapping with french content
	 */
	@Override
	public XContentBuilder mapping() throws Exception {
		XContentBuilder xbMapping = 
			jsonBuilder()
				.startObject()
					.startObject("page")
						.startObject("properties")
							.startObject("source")
								.field("type", "string")
							.endObject()
							.startObject("title")
								.field("type", "string")
								.field("analyzer", "french")
							.endObject()
							.startObject("description")
								.field("type", "string")
								.field("analyzer", "french")
							.endObject()
							.startObject("author")
								.field("type", "string")
							.endObject()
							.startObject("link")
								.field("type", "string")
							.endObject()
						.endObject()
					.endObject()
				.endObject();
		return xbMapping;
	}

	/**
	 * 1 RSS feed :
	 * <ul>
	 *   <li>http://www.lemonde.fr/rss/une.xml every 10 seconds and ignoring Ttl
	 * </ul>
	 */
    @Override
    public void addFeeds(XContentBuilder xcb) {
        addRiver(xcb, "http://www.lemonde.fr/rss/une.xml", null, update_rate);
	}
	

	@Test
	public void rss_update_rate_does_not_change() throws Exception {

        checkUpdateRateHelper(update_rate, false);

    }
}
