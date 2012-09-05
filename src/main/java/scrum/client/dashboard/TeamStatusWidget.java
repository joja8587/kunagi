/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scrum.client.admin.User;
import scrum.client.common.AScrumWidget;
import scrum.client.project.Project;
import scrum.client.sprint.Task;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class TeamStatusWidget extends AScrumWidget {

	private HTML html;

	@Override
	protected Widget onInitialization() {
		html = new HTML();
		return html;
	}

	@Override
	protected void onUpdate() {
		Project project = getCurrentProject();
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='TeamTasksWidget'>");

		List<User> lazyUsers = new ArrayList<User>();
		List<User> team = new LinkedList<User>(project.getTeamMembers());
		Collections.sort(team, User.NAME_COMPARATOR);
		User currentUser = getCurrentUser();

		sb.append("<table cellspacing=\"0\" cellpadding=\"0\"><tr bgcolor=\"#FFFFAA\">");
		sb.append("<td width=\"80\">User</td>");
		sb.append("<td width=\"60\">Claimed</td>");
		sb.append("<td width=\"60\">Burned</td>");
		sb.append("<td width=\"60\">Progress</td></tr>");

		if (team.contains(currentUser)) {
			team.remove(currentUser);
			team.add(0, currentUser);
		}
		for (User user : team) {
			List<Task> tasks = project.getClaimedTasks(user);
			int burned = 0;
			int claimed = 0;
			for (Task task : tasks) {
				burned += task.getBurnedWork();
				claimed += task.getBurnedWork() + task.getRemainingWork();
			}
			int progress = (int) ((burned / (double) claimed) * 100);
			sb.append("<tr>");
			sb.append("<td>" + currentUser.getName() + "</td>");
			sb.append("<td>" + claimed + "</td>");
			sb.append("<td>" + burned + "</td>");
			sb.append("<td>" + progress + "</td>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		html.setHTML(sb.toString());
	}
}
