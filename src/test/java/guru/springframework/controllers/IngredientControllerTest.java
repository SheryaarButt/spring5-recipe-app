package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController ingredientController;
    MockMvc mockMvc;

    RecipeCommand testRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testRecipe = RecipeCommand.builder().id(1L).build();
        ingredientController = new IngredientController(recipeService,ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void showRecipeIngredients() {
        when(recipeService.getRecipe(1L)).thenReturn(testRecipe);

        try{
            mockMvc.perform(get("/recipe/1/ingredients"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/ingredient/list"))
                    .andExpect(model().attribute("recipe",testRecipe));
        } catch(Exception e){
            fail(e.getMessage());
        }

        verify(recipeService,times(1)).getRecipe(1L);
    }

    @Test
    public void deleteIngredient(){

        try{
            mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/recipe/1/ingredients"));
        } catch(Exception e){
            fail(e.getMessage());
        }

        verify(ingredientService,times(1)).deleteIngredient(2L);
    }
}