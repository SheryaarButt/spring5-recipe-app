package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitOfMeasureConverterTest {

    UnitOfMeasure testEntity;
    UnitOfMeasureCommand testCommand;

    UnitOfMeasureConverter unitOfMeasureConverter;

    @Before
    public void setUp() throws Exception{
        testEntity = UnitOfMeasure.builder().id(1L).uom("TestMeasureEntity").build();
        testCommand = UnitOfMeasureCommand.builder().id(2L).uom("TestMeasureCommand").build();
        unitOfMeasureConverter = new UnitOfMeasureConverter();
    }

    @Test
    public void convertEntityToCommand() {
        UnitOfMeasureCommand returnCommand = unitOfMeasureConverter.convertEntityToCommand(testEntity);
        assertEquals(testEntity.getId(),returnCommand.getId());
        assertEquals(testEntity.getUom(),returnCommand.getUom());

    }

    @Test
    public void convertCommandToEntity() {
        UnitOfMeasure returnEntity = unitOfMeasureConverter.convertCommandToEntity(testCommand);
        assertEquals(testCommand.getId(),returnEntity.getId());
        assertEquals(testCommand.getUom(),returnEntity.getUom());
    }
}