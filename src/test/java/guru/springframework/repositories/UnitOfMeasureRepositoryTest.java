package guru.springframework.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

//Brings in spring context
@RunWith(SpringRunner.class)
//Configures spring data jpa and brings up embedded database
@DataJpaTest
public class UnitOfMeasureRepositoryTest {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUom() {

        String returnVal = unitOfMeasureRepository.findByUom("teaspoon").get().getUom();
        assertEquals("teaspoon",returnVal);

    }
}