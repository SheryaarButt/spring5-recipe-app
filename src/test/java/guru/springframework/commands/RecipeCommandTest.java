package guru.springframework.commands;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecipeCommandTest {

    RecipeCommand recipeCommand;
    IngredientCommand testIngredient1;
    IngredientCommand testIngredient2;
    IngredientCommand testIngredient3;
    UnitOfMeasureCommand testUom2;

    IngredientCommand testIngredient2Update;
    UnitOfMeasureCommand testUom2Update;

    @Before
    public void setUp(){
        testIngredient1 = IngredientCommand.builder().id(1L).amount(new BigDecimal(5)).description("cardamom").build();
        testIngredient2 = IngredientCommand.builder().id(2L).amount(new BigDecimal(2)).description("rosemary").build();
        testUom2 = UnitOfMeasureCommand.builder().id(1L).uom("bunch").build();
        testIngredient2.setUnitOfMeasure(testUom2);
        testIngredient3 = IngredientCommand.builder().id(3L).amount(new BigDecimal(6)).description("garlic").build();

        testIngredient2Update = IngredientCommand.builder().id(2L).amount(new BigDecimal(99)).description("thyme").build();
        testUom2Update = UnitOfMeasureCommand.builder().id(2L).uom("pinch").build();
        testIngredient2Update.setUnitOfMeasure(testUom2Update);

        recipeCommand = RecipeCommand.builder().id(1L).build();
        recipeCommand.addIngredient(testIngredient1);
        recipeCommand.addIngredient(testIngredient2);
        recipeCommand.addIngredient(testIngredient3);
    }

    @Test
    public void updateIngredient() {
        assertFalse(testIngredient2Update.getDescription().equals(testIngredient2.getDescription()));
        assertFalse(testIngredient2Update.getAmount().equals(testIngredient2.getAmount()));
        assertFalse(testIngredient2Update.getUnitOfMeasure().getId().equals(testIngredient2.getUnitOfMeasure().getId()));
        assertFalse(testIngredient2Update.getUnitOfMeasure().getUom().equals(testIngredient2.getUnitOfMeasure().getUom()));

        recipeCommand.updateIngredient(testIngredient2Update.getId(),testIngredient2Update);

        assertTrue(testIngredient2Update.getDescription().equals(testIngredient2.getDescription()));
        assertTrue(testIngredient2Update.getAmount().equals(testIngredient2.getAmount()));
        assertTrue(testIngredient2Update.getUnitOfMeasure().getId().equals(testIngredient2.getUnitOfMeasure().getId()));
        assertTrue(testIngredient2Update.getUnitOfMeasure().getUom().equals(testIngredient2.getUnitOfMeasure().getUom()));
    }
}