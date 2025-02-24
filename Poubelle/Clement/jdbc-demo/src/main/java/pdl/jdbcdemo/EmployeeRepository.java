package pdl.jdbcdemo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class EmployeeRepository implements InitializingBean {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public static String fichier[] = new String[] { "arbre.jpeg", "test.jpeg" };
  public static String names[] = new String[] { "arbre", "chien" };

  @Override
  public void afterPropertiesSet() throws Exception {
    // Drop table
    jdbcTemplate.execute("DROP TABLE IF EXISTS employee");

    // Create table
    this.jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS employee (id bigserial PRIMARY KEY, name character varying(255), fichier character varying(255))");

    // Insert rows
    if( fichier.length > names.length){
      for(int i = 0; i < fichier.length; i++){
            jdbcTemplate.update("INSERT INTO employee (name, fichier) VALUES (?, ?)", names[i], fichier[i]);
      }
    } else {
      for(int i = 0; i < names.length; i++){
            jdbcTemplate.update("INSERT INTO employee (name, fichier) VALUES (?, ?)", names[i], fichier[i]);
      }
    }

  }

  public int getNbEmployees() {
    return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM employee", Integer.class);
  }

  public String getEmployeeName(long id) {
    return jdbcTemplate.queryForObject("SELECT name FROM employee WHERE id = ?", String.class, id);
  }

}
