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

import ilarkesto.di.app.BackupApplicationDataDirTask;
import ilarkesto.ui.web.HtmlRenderer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scrum.server.WebSession;
import scrum.server.common.AHttpServlet;

public class BackupServlet extends AHttpServlet {

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

		// TODO show message
		webApplication.getTaskManager().start(webApplication.autowire(new BackupApplicationDataDirTask()));

		HtmlRenderer html = createDefaultHtmlWithHeader(resp, "Backup running", 3, "admin.html");
		html.startBODY();
		html.H1("Backup initiated");
		html.startP();
		html.text("All clients are blocked until backup is completed.");
		html.endP();
		html.endBODY();
		html.endHTML();
		html.flush();
	}

}
