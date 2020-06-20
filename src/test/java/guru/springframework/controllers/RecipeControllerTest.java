package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeControllerTest {

    MockMvc mockMvc;

    RecipeController recipeController;

    @Mock
    RecipeService recipeService;
    @Mock
    RecipeConverter recipeConverter;

    RecipeCommand testRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testRecipe = RecipeCommand.builder().id(1L).build();
        recipeController = new RecipeController(recipeService,recipeConverter);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void showExists() {

        when(recipeService.getRecipe(anyLong())).thenReturn(testRecipe);

        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                    .andExpect(MockMvcResultMatchers.model().attribute("recipe",testRecipe));
        } catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void showNotExists() {
        when(recipeService.getRecipe(anyLong())).thenReturn(null);
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("recipe/error"))
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("recipe"));
        } catch(Exception e){
            fail(e.getMessage());
        }
        verify(recipeService,times(1)).getRecipe(anyLong());

    }

    @Test
    public void showInvalidId() {

        when(recipeService.getRecipe(anyLong())).thenReturn(testRecipe);

        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1sasd"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("recipe/error"))
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("recipe"));
        } catch(Exception e){
            fail(e.getMessage());
        }
        verify(recipeService,never()).getRecipe(any());
    }




}