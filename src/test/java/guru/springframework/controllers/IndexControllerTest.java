package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    @Mock
    private Model model;

    @Mock
    private RecipeService recipeService;

    private IndexController indexController;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMocMVC() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("index"));
        } catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

    }

    @Test
    public void index() {

        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe1.setDescription("Recipe1");
        recipe2.setDescription("Recipe2");
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipes);

        String returnTest = indexController.index(model);

        assertEquals(returnTest,"index");

        verify(recipeService,times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"),eq(recipes));


    }

}