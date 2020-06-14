package guru.springframework.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

//Brings in spring context
@RunWith(SpringRunner.class)

//Boots spring as normal
@SpringBootTest

//Configures spring data jpa and brings up embedded database - not a full spring boot
//@DataJpaTest
public class UnitOfMeasureRepositoryIT {

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