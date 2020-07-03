package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;
    MockMvc mockMvc;

    RecipeCommand testRecipe;

    IngredientCommand testIngredient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testRecipe = RecipeCommand.builder().id(1L).build();
        testIngredient = IngredientCommand.builder().id(2L).build();
        ingredientController = new IngredientController(recipeService,ingredientService,unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void showRecipeIngredients() {
        when(recipeService.getRecipe(1L)).thenReturn(testRecipe);

        try{
            mockMvc.perform(get("/recipe/1/ingredient/showAll"))
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
                    .andExpect(view().name("redirect:/recipe/1/ingredient/showAll"));
        } catch(Exception e){
            fail(e.getMessage());
        }

        verify(ingredientService,times(1)).deleteIngredient(2L);
    }

    @Test
    public void showIngredient(){
        when(ingredientService.getIngredient(2L)).thenReturn(testIngredient);
        try{
            mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                    .andExpect(view().name("recipe/ingredient/show"))
                    .andExpect(model().attribute("ingredient",testIngredient))
                    .andExpect(status().isOk());
        } catch(Exception e){
            fail(e.getMessage());
        }
        verify(ingredientService,times(1)).getIngredient(anyLong());
    }

    @Test
    public void newIngredientForm() {
        try{
            mockMvc.perform(get("/recipe/1/ingredient/new"))
                    .andExpect(view().name("recipe/ingredient/ingredientform"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("ingredient"))
                    .andExpect(model().attribute("recipeId","1"))
                    .andExpect(model().attributeExists("uomList"));
        }catch(Exception e){
            fail(e.getMessage());
        }

    }

    @Test
    public void updateIngredientForm() {
        when(ingredientService.getIngredient(2L)).thenReturn(testIngredient);
        try{
            mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                    .andExpect(view().name("recipe/ingredient/ingredientform"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("ingredient",testIngredient))
                    .andExpect(model().attribute("recipeId","1"))
                    .andExpect(model().attributeExists("uomList"));
        }catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void addOrUpdateIngredient() {
        try{
            mockMvc.perform(post("/recipe/1/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/recipe/1/ingredient/showAll"));
        } catch(Exception e){
            fail(e.getMessage());
        }
        verify(recipeService,times(1)).saveIngredient(any(),any());
    }
}