package pdl.jdbcdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeRepositoryTests {

  @Autowired
  private EmployeeRepository repository;

  @Test
  void testGetNbEmployees() {
    assertEquals(repository.getNbEmployees(), EmployeeRepository.names.length);
  }

  @Test
  void testGetEmployeeName() {
    for (int i=0; i<EmployeeRepository.names.length; ++i) {
      assertEquals(repository.getEmployeeName(i+1), EmployeeRepository.names[i]);
    }
  }
}
