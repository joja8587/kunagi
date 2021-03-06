/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.server.admin;

import ilarkesto.core.logging.LogRecord;
import ilarkesto.logging.DefaultLogRecordHandler;
import ilarkesto.ui.web.HtmlRenderer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scrum.server.WebSession;
import scrum.server.common.AHttpServlet;

public class LogsServlet extends AHttpServlet {

	@Override
	protected void onRequest(HttpServletRequest req, HttpServletResponse resp, WebSession session) throws IOException {
		tokenLogin(req, resp, session);

		User user = session.getUser();
		if (user == null) {
			redirectToLogin(req, resp, session);
			return;
		}

		if (!user.isAdmin()) {
			resp.sendError(403);
			return;
		}

		HtmlRenderer html = createDefaultHtmlWithHeader(resp, "Latest logs");

		html.startBODY().setStyle("font-size: 10px");

		adminLinks(html, req);

		List<LogRecord> logs = DefaultLogRecordHandler.getLatestRecords();
		logsTable(html, logs);

		adminLinks(html, req);

		html.SCRIPTjavascript(null, "window.onload=function() { window.scrollTo(0, document.body.scrollHeight); }");

		html.endBODY();
		html.endHTML();
		html.flush();
	}

}
