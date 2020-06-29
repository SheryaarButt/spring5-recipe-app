package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
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
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
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
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
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
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1sasd/show"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch(Exception e){
            fail(e.getMessage());
        }
        verify(recipeService,never()).getRecipe(any());
    }

    @Test
    public void updateIdNotExist(){

        when(recipeService.getRecipe(921L)).thenReturn(null);

        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/921/update"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("recipe"))
                    .andExpect(MockMvcResultMatchers.view().name("recipe/error"));
        } catch (Exception e){
            fail(e.getMessage());
        }

        verify(recipeService,times(1)).getRecipe(921L);
    }

    @Test
    public void updateIdInvalidFormat(){

        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/92sfa1/update"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e){
            fail(e.getMessage());
        }

        verify(recipeService,never()).getRecipe(any());
    }

    @Test
    public void updateIdInvalidFormat2(){

        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/01/update"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e){
            fail(e.getMessage());
        }

        verify(recipeService,never()).getRecipe(any());
    }

    @Test
    public void updateIdSuccess(){

        when(recipeService.getRecipe(1L)).thenReturn(testRecipe);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                    .andExpect(MockMvcResultMatchers.model().attribute("recipe",testRecipe));
        } catch(Exception e) {
            fail(e.getMessage());
        }
        verify(recipeService,times(1)).getRecipe(1L);
    }

    @Test
    public void saveNewPost(){
        when(recipeService.saveRecipe(any())).thenReturn(testRecipe);
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("id",""))
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("recipe"))
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("command"))
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/show"));
        } catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void updatePost(){
        when(recipeService.saveRecipe(any())).thenReturn(testRecipe);
        try{
            mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id","1"))
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("recipe"))
                    .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("command"))
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/show"));
        } catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteRecipe(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
        } catch(Exception e){
            fail(e.getMessage());
        }
        verify(recipeService,times(1)).deleteRecipe(1L);
    }
}
