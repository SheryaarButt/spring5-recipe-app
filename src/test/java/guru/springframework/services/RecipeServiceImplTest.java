package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
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
    @Mock
    RecipeConverter recipeConverter;

    RecipeService recipeService;

    Recipe testRecipe2;
    Recipe testRecipe3;

    List<Recipe> testRecipes;

    RecipeCommand testRecipeCommand1;
    RecipeCommand testRecipeCommand2;
    RecipeCommand testRecipeCommand3;

    Recipe testRecipe;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,recipeConverter);

        testRecipe = Recipe.builder().id(1L).build();
        testRecipe2 = Recipe.builder().id(2L).build();
        testRecipe3 = Recipe.builder().id(3L).build();

        testRecipes = new ArrayList<>();
        testRecipes.add(testRecipe);
        testRecipes.add(testRecipe2);
        testRecipes.add(testRecipe3);

        testRecipeCommand1 = RecipeCommand.builder().id(1L).build();
        testRecipeCommand2 = RecipeCommand.builder().id(1L).build();
        testRecipeCommand3 = RecipeCommand.builder().id(1L).build();

    }

    @Test
    public void getRecipes() {
        when(recipeRepository.findAll()).thenReturn(testRecipes);
        when(recipeConverter.convertEntityToCommand(testRecipe)).thenReturn(testRecipeCommand1);
        when(recipeConverter.convertEntityToCommand(testRecipe2)).thenReturn(testRecipeCommand2);
        when(recipeConverter.convertEntityToCommand(testRecipe3)).thenReturn(testRecipeCommand3);

        Set<RecipeCommand> returnSet = recipeService.getRecipes();

        assertEquals(returnSet.size(),testRecipes.size());

        verify(recipeRepository,times(1)).findAll();
        verify(recipeConverter,times(3)).convertEntityToCommand(any());
    }

    @Test
    public void getRecipeExists() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));
        when(recipeConverter.convertEntityToCommand(testRecipe)).thenReturn(testRecipeCommand1);

        assertEquals(testRecipe.getId(),recipeService.getRecipe(1L).getId());

        verify(recipeRepository,times(1)).findById(anyLong());

    }
    @Test
    public void getRecipeNotExists() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(recipeConverter.convertEntityToCommand(null)).thenReturn(null);

        assertNull(recipeService.getRecipe(1L));

        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    public void deleteRecipe(){
        recipeService.deleteRecipe(1L);
        verify(recipeRepository,times(1)).deleteById(1L);
    }

}