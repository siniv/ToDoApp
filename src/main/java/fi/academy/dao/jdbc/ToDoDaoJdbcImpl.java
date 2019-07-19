package fi.academy.dao.jdbc;

import fi.academy.Task;
import fi.academy.dao.ToDoDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jdbc")
public class ToDoDaoJdbcImpl implements ToDoDao {
    private Connection con;

    public ToDoDaoJdbcImpl() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "sini1");
    }

    @Override
    public List<Task> showAll() {
        String sql = "SELECT * FROM task";
        List<Task> results = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (ResultSet rs = pstmt.executeQuery(); rs.next() ;) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                results.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return results;
    }

    //tulee myöhemmin
    @Override
    public Optional<Task> showTasksWithId(int id) {
        return Optional.empty();
    }


    @Override
    public int add(Task task) {
        int key = -1;
        String sql = "INSERT INTO task(name) VALUES(?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, task.getName());
            stmt.execute();
            stmt.close();
            ResultSet keys = stmt.getGeneratedKeys();
            while (keys.next()) {
                key = keys.getInt(1);
                task.setId(key);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    @Override
    public Task delete(int id) {
        Task deleted = new Task();
        String sql = "SELECT * FROM task WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                deleted.setId(rs.getInt("id"));
                deleted.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql2 = "DELETE FROM task WHERE id = ?";
        try (PreparedStatement pr = con.prepareStatement(sql2)) {
            pr.setInt(1, id);
            pr.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }


    //tulee myöhemmin
    @Override
    public boolean modify(int id, Task uusi) {
        String sql = "UPDATE task SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, uusi.getName());
            stmt.setInt(2, id);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                uusi.setId(rs.getInt("id"));
                uusi.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
