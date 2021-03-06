package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Runners.DBConn;

public class Database {
	static Statement stmt = null;
	
	public static int addTask(Task t){
		PreparedStatement prep = null;
		ResultSet rs = null;
		int id = -1;
		try {
			prep = DBConn.getConnection().prepareStatement("INSERT INTO Tasks VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
			prep.setInt(1, t.getId());
			prep.setInt(2, t.getUid());
			prep.setString(3, t.getName());
			prep.setString(4, t.getDescription());
			prep.setLong(5, t.getDueDate());
			prep.setBoolean(6, t.getFinishFlag());
			prep.setBoolean(7, t.getOnFlag());
			prep.setInt(8, ModelControl.getTypeID(t.getType()));
			prep.setInt(9, ModelControl.getClassID(t.getClassAbr()));
			prep.setInt(10, t.getNoticePeriod());
			prep.setInt(11, t.getTimeToComplete());
			prep.setLong(12, t.getScheduledStartTime());
			prep.setLong(13, t.getScheduledEndTime());
			prep.execute();
			prep.close();
			prep = null;
		}catch(SQLException e) {
			System.out.println("\nError DB01:\n" + e.getMessage());
		}
		
		try {
			prep = DBConn.getConnection().prepareStatement("SELECT id FROM Tasks ORDER BY id DESC LIMIT 1;");
			rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("\nError DB01b:\n" + e.getMessage());
		}
		
		return id;
	}
	public static int addAdmin(Admin a) {
		PreparedStatement prep = null;
		ResultSet rs = null;
		int id = -1;
		try {
			prep = DBConn.getConnection().prepareStatement("INSERT INTO Admins VALUES (?,?,?);");
			prep.setInt(1, a.getId());
			prep.setString(2, a.getUsername());
			prep.setString(3, a.getPassword());
			prep.execute();
			prep.close();
			prep = null;
		}catch(SQLException e) {
			System.out.println("\nError DB02:\n" + e.getMessage());
		}
		
		try {
			prep = DBConn.getConnection().prepareStatement("SELECT id FROM Admins ORDER BY id DESC LIMIT 1;");
			rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("\nError DB02b:\n" + e.getMessage());
		}
		return id;
	}
	public static int addClass(Class c) {
		PreparedStatement prep = null;
		ResultSet rs = null;
		int id = -1;
		try {
			prep = DBConn.getConnection().prepareStatement("INSERT INTO Classes VALUES (?,?,?,?,?,?,?,?,?);");
			prep.setInt(1, c.getId());
			prep.setInt(2, c.getUid());
			prep.setString(3, c.getName());
			prep.setString(4, c.getAbbreviation());
			prep.setString(5, c.getDetails());
			prep.setInt(6, c.getTotalAssignments());
			prep.setString(7, c.getDaysOfWeek(""));
			prep.setString(8, c.getStartTime(""));
			prep.setString(9, c.getEndTime(""));
			prep.execute();
			prep.close();
			prep = null;
		}catch(SQLException e) {
			System.out.println("\nError DB03:\n" + e.getMessage());
		}
		
		try {
			prep = DBConn.getConnection().prepareStatement("SELECT id FROM Classes ORDER BY id DESC LIMIT 1;");
			rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("\nError DB03b:\n" + e.getMessage());
		}
		
		return id;
	}
	public static int addProject(Project p) {
		PreparedStatement prep = null;
		ResultSet rs = null;
		int id = -1;
		try {
			prep = DBConn.getConnection().prepareStatement("INSERT INTO Projects VALUES (?,?,?,?,?,?);");
			prep.setInt(1, p.getId());
			prep.setInt(2, p.getUid());
			prep.setString(3, p.getName());
			prep.setString(4, p.getDescription());
			prep.setString(5, p.getCurrentStep());
			prep.setInt(6, p.getPriorityLevel());
			prep.execute();
			prep.close();
			prep = null;
		}catch(SQLException e) {
			System.out.println("\nError DB04:\n" + e.getMessage());
		}
		
		try {
			prep = DBConn.getConnection().prepareStatement("SELECT id FROM Projects ORDER BY id DESC LIMIT 1;");
			rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("\nError DB04b:\n" + e.getMessage());
		}
		
		return id;
	}
	public static int addTaskType(TaskType tt) {
		PreparedStatement prep = null;
		ResultSet rs = null;
		int id = -1;
		try {
			prep = DBConn.getConnection().prepareStatement("INSERT INTO TaskTypes VALUES (?,?,?,?,?,?,?);");
			prep.setInt(1, tt.getId());
			prep.setInt(2, tt.getUid());
			prep.setString(3, tt.getName());
			prep.setString(4, tt.getDescription());
			prep.setInt(5, tt.getWarningPeriod());
			prep.setInt(6, tt.getTimeToComplete());
			prep.setInt(7, tt.getTotalAssignments());
			prep.execute();
			prep.close();
			prep = null;
		}catch(SQLException e) {
			System.out.println("\nError DB05:\n" + e.getMessage());
		}
		
		try {
			prep = DBConn.getConnection().prepareStatement("SELECT id FROM TaskTypes ORDER BY id DESC LIMIT 1;");
			rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("\nError DB05b:\n" + e.getMessage());
		}

		return id;
	}
	public static int addUser(User u) {
		PreparedStatement prep = null;
		ResultSet rs = null;
		int id = -1;
		try {
			prep = DBConn.getConnection().prepareStatement("INSERT INTO Users VALUES (?,?,?);");
			prep.setInt(1, u.getId());
			prep.setString(2, u.getUsername());
			prep.setString(3, u.getPassword());
			prep.execute();
			prep.close();
			prep = null;
		}catch(SQLException e) {
			System.out.println("\nError DB06:\n" + e.getMessage());
		}
		
		try {
			prep = DBConn.getConnection().prepareStatement("SELECT id FROM Users ORDER BY id DESC LIMIT 1;");
			rs = prep.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("\nError DB06b:\n" + e.getMessage());
		}
		
		return id;
	}
	public static void updateTask(Task t) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("UPDATE Tasks SET name = ?, description = ?, "
					+ "dueDate = ?, finishFlag = ?, onFlag = ?, typeID = ?, classID = ?, noticePeriod = ?,"
					+ " timeToComplete = ?, scheduledStartTime = ?, scheduledEndTime = ? WHERE id = ?;");
			prep.setString(1, t.getName());
			prep.setString(2, t.getDescription());
			prep.setLong(3, t.getDueDate());
			prep.setBoolean(4, t.getFinishFlag());
			prep.setBoolean(5, t.getOnFlag());
			prep.setInt(6, ModelControl.getTypeID(t.getType()));
			prep.setInt(7, ModelControl.getClassID(t.getClassAbr()));
			prep.setInt(8,  t.getNoticePeriod());
			prep.setInt(9, t.getTimeToComplete());
			prep.setLong(10,  t.getScheduledStartTime());
			prep.setLong(11, t.getScheduledEndTime());
			prep.setInt(12, t.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB07:\n" + e.getMessage());
		}
	}
	public static void updateAdmin(Admin a) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("UPDATE Admins SET username = ?, password = ? WHERE id = ?;");
			prep.setString(1,  a.getUsername());
			prep.setString(2,  a.getPassword());
			prep.setInt(3,  a.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB08:\n" + e.getMessage());
		}
	}
	public static void updateClass(Class c) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("UPDATE Classes SET name = ?, abbreviation = ?, details = ?, "
					+ "totalAssignments = ?, daysOfWeek = ?, startTime = ?, endTime = ? WHERE id = ?;");
			prep.setString(1, c.getName());
			prep.setString(2, c.getAbbreviation());
			prep.setString(3, c.getDetails());
			prep.setInt(4, c.getTotalAssignments());
			prep.setString(5,  c.getDaysOfWeek(""));
			prep.setString(6,  c.getStartTime(""));
			prep.setString(7,  c.getEndTime(""));
			prep.setInt(8, c.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB09:\n" + e.getMessage());
		}
	}
	public static void updateProject(Project p) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("UPDATE Projects SET name = ?, description = ?, currentStep = ?,"
					+ "priorityLevel = ? WHERE id = ?;");
			prep.setString(1, p.getName());
			prep.setString(2, p.getDescription());
			prep.setString(3, p.getCurrentStep());
			prep.setInt(4, p.getPriorityLevel());
			prep.setInt(5,  p.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB10:\n" + e.getMessage());
		}
	}
	public static void updateTaskType(TaskType tt) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("UPDATE TaskTypes SET name = ?, description = ?, warningPeriod = ?, "
					+ "timeToComplete = ?, totalAssignments = ? WHERE id = ?;");
			prep.setString(1, tt.getName());
			prep.setString(2, tt.getDescription());
			prep.setInt(3, tt.getWarningPeriod());
			prep.setInt(4, tt.getTimeToComplete());
			prep.setInt(5, tt.getTotalAssignments());
			prep.setInt(6,  tt.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB11:\n" + e.getMessage());
		}
	}
	public static void updateUser(User u) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("UPDATE Users SET username = ?, password = ? WHERE id = ?;");
			prep.setString(1,  u.getUsername());
			prep.setString(2,  u.getPassword());
			prep.setInt(3,  u.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB12:\n" + e.getMessage());
		}
	}
	public static void deleteTask(Task t) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("DELETE FROM Tasks WHERE id = ?;");
			prep.setInt(1, t.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB13:\n" + e.getMessage());
		}
	}
	public static void deleteAdmin(Admin a) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("DELETE From Admins WHERE id = ?;");
			prep.setInt(1,  a.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB14:\n" + e.getMessage());
		}
	}
	public static void deleteClass(Class c) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("DELETE FROM Classes WHERE id = ?;");
			prep.setInt(1, c.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB15:\n" + e.getMessage());
		}
	}
	public static void deleteProject(Project p) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("DELETE FROM Projects WHERE id = ?;");
			prep.setInt(1, p.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB16:\n" + e.getMessage());
		}
	}
	public static void deleteTaskType(TaskType tt) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("DELETE FROM TaskTypes WHERE id = ?;");
			prep.setInt(1, tt.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB17:\n" + e.getMessage());
		}
	}
	public static void deleteUser(User u) {
		PreparedStatement prep = null;
		try {
			prep = DBConn.getConnection().prepareStatement("DELETE FROM Users WHERE id = ?;");
			prep.setInt(1,  u.getId());
			prep.execute();
			prep.close();
		}catch(SQLException e) {
			System.out.println("\nError DB18:\n" + e.getMessage());
		}
	}
	public static ResultSet getTasks(int id) {
		PreparedStatement prep = null;
		if(id == -1) {
			try {
				stmt = DBConn.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks;");
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB19a:\n" + e.getMessage());
			}
		}
		else {
			try {
				prep = DBConn.getConnection().prepareStatement("SELECT * FROM Tasks WHERE uid = ?;");
				prep.setInt(1,  id);
				ResultSet rs = prep.executeQuery();
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB19:\n" + e.getMessage());
			}
		}
		return null;
	}
	public static ResultSet getAdmins() {
		try {
			stmt = DBConn.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Admins;");
			return rs;
		}catch(SQLException e) {
			System.out.println("\nError DB20:\n" + e.getMessage());
		}
		return null;
	}
	public static ResultSet getClasses(int id) {
		PreparedStatement prep = null;
		if(id == -1) {
			try {
				stmt = DBConn.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Classes;");
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB21a:\n" + e.getMessage());
			}
		}
		else {
			try {
				prep = DBConn.getConnection().prepareStatement("SELECT * FROM Classes WHERE uid = ?;");
				prep.setInt(1,  id);
				ResultSet rs = prep.executeQuery();
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB21:\n" + e.getMessage());
			}
		}
		
		return null;
	}
	public static ResultSet getProjects(int id) {
		PreparedStatement prep = null;
		if(id == -1) {
			try {
				stmt = DBConn.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Projects;");
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB22a:\n" + e.getMessage());
			}
		}
		else {
			try {
				prep = DBConn.getConnection().prepareStatement("SELECT * FROM Projects WHERE uid = ?;");
				prep.setInt(1,  id);
				ResultSet rs = prep.executeQuery();
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB22:\n" + e.getMessage());
			}
		}
		return null;
	}
	public static ResultSet getTaskTypes(int id) {
		PreparedStatement prep = null;
		if(id == -1) {
			try {
				stmt = DBConn.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM TaskTypes;");
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB23a:\n" + e.getMessage());
			}
		}
		else {
			try {
				prep = DBConn.getConnection().prepareStatement("SELECT * FROM TaskTypes WHERE uid = ?;");
				prep.setInt(1,  id);
				ResultSet rs = prep.executeQuery();
				return rs;
			}catch(SQLException e) {
				System.out.println("\nError DB23:\n" + e.getMessage());
			}
		}
		return null;
	}
	public static ResultSet getUsers() {
		try {
			stmt = DBConn.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
			return rs;
		}catch(SQLException e) {
			System.out.println("\nError DB24:\n" + e.getMessage());
		}
		return null;
	}
}
