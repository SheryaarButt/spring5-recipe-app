package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    RecipeService recipeService;

    List<Recipe> testRecipes;

    Recipe testRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);

        testRecipe = Recipe.builder().id(1L).build();
        testRecipes = new ArrayList<>();
        testRecipes.add(Recipe.builder().id(1L).build());
        testRecipes.add(Recipe.builder().id(2L).build());
        testRecipes.add(Recipe.builder().id(3L).build());

    }

    @Test
    public void getRecipes() {
        when(recipeRepository.findAll()).thenReturn(testRecipes);

        Set<Recipe> returnSet = recipeService.getRecipes();

        assertEquals(returnSet.size(),testRecipes.size());

        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void getRecipeExists() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));

        assertEquals(recipeService.getRecipe(1L),testRecipe);

        verify(recipeRepository,times(1)).findById(anyLong());

    }
    @Test
    public void getRecipeNotExists() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(recipeService.getRecipe(1L));

        verify(recipeRepository,times(1)).findById(anyLong());

    }
}