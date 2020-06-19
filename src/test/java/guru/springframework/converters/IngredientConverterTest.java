package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class IngredientConverterTest {

    Ingredient testEntity;
    IngredientCommand testCommand;

    UnitOfMeasure uomTestEntity;
    UnitOfMeasureCommand uomTestCommand;

    IngredientConverter ingredientConverter;

    @Mock
    UnitOfMeasureConverter unitOfMeasureConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uomTestEntity = UnitOfMeasure.builder().id(1L).uom("testUom").build();
        uomTestCommand = UnitOfMeasureCommand.builder().id(1L).uom("testUom").build();
        testEntity = Ingredient.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(10.0))
                .description("TestIngEntity")
                .unitOfMeasure(uomTestEntity).build();
        testCommand = IngredientCommand.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(10.0))
                .description("TestIngCommand")
                .unitOfMeasure(uomTestCommand)
                .build();
        ingredientConverter = new IngredientConverter(unitOfMeasureConverter);
    }

    @Test
    public void convertEntityToCommand() {
        when(unitOfMeasureConverter.convertEntityToCommand(uomTestEntity)).thenReturn(uomTestCommand);
        IngredientCommand returnCommand = ingredientConverter.convertEntityToCommand(testEntity);
        assertEquals(testEntity.getId(),returnCommand.getId());
        assertEquals(testEntity.getAmount(),returnCommand.getAmount());
        assertEquals(testEntity.getDescription(),returnCommand.getDescription());
        assertEquals(testEntity.getUnitOfMeasure().getId(),returnCommand.getUnitOfMeasure().getId());
        assertEquals(testEntity.getUnitOfMeasure().getUom(),returnCommand.getUnitOfMeasure().getUom());
    }

    @Test
    public void convertCommandToEntity() {
        when(unitOfMeasureConverter.convertCommandToEntity(uomTestCommand)).thenReturn(uomTestEntity);
        Ingredient returnEntity = ingredientConverter.convertCommandToEntity(testCommand);
        assertEquals(testCommand.getId(),returnEntity.getId());
        assertEquals(testCommand.getAmount(),returnEntity.getAmount());
        assertEquals(testCommand.getDescription(),returnEntity.getDescription());
        assertEquals(testCommand.getUnitOfMeasure().getId(),returnEntity.getUnitOfMeasure().getId());
        assertEquals(testCommand.getUnitOfMeasure().getUom(),returnEntity.getUnitOfMeasure().getUom());
    }
}