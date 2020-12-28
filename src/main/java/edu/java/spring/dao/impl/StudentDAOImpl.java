package edu.java.spring.dao.impl;

import edu.java.spring.dao.StudentDAO;
import edu.java.spring.model.Student;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class StudentDAOImpl implements StudentDAO, DisposableBean {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    private static Logger LOGGER = (Logger) Logger.getInstance(StudentDAOImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    private void createTableIfNotExist() throws SQLException {
        DatabaseMetaData dbmd = dataSource.getConnection().getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "STUDENT", null);
        if (rs.next()) {
            LOGGER.info("Table " + rs.getString("TABLE_NAME") + " already exists !");
            return;
        }
        jdbcTemplate.execute("CREATE TABLE STUDENT ("
                + " id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY ( START WITH 1, INCREMENT BY 1),"
                + " name VARCHAR(1000),"
                + " age INTEGER)");
    }


    @Override
    public void insert(Student student) {
        String insertQuery = "INSERT INTO STUDENT(name,age) VALUES (?,?)";
        String name = student.getName();
        int age = student.getAge();
        jdbcTemplate.update(insertQuery, name, age);
        LOGGER.info("Created Record Name = " + name + " Age = " + age);

    }

    @Override
    public void delete(String id) {
        jdbcTemplate.execute("DELETE FROM STUDENT WHERE ID =" + id);
    }

    @Override
    public List<Student> list() {
        return jdbcTemplate.query("SELECT * FROM STUDENT", new StudentRowMapper());

    }

    @Override
    public Student get(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM STUDENT WHERE ID = " + id, new StudentRowMapper());
    }


    private final static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setAge(resultSet.getInt("age"));
            LOGGER.info(student);
            return student;
        }
    }

    public void update(Student student) {
        String name = student.getName();
        int id = student.getId();
        jdbcTemplate.update("UPDATE STUDENT SET NAME = ? WHERE ID = ?", name, id);
    }


    @Override
    public void destroy() throws Exception {
        DriverManager.getConnection("jdbc:derby:C:/Java/sampledb2;shutdown=true");
    }

}
