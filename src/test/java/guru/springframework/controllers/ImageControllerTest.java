package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;
    MockMultipartFile mockMultipartFile;

    ImageController imageController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ImageController imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void updateImageForm() {
        //given

        //when
        try {
            mockMvc.perform(get("/recipe/1/image/update"))
                        .andExpect(model().attributeExists("recipeId"))
                        .andExpect(view().name("recipe/imageuploadform"))
                        .andExpect(status().isOk());
        } catch(Exception e){
            fail(e.getMessage());
        }

    }

    @Test
    public void updateImage() {

        //given
        mockMultipartFile = new MockMultipartFile("imagefile","testing.txt","text/plain","Spring".getBytes());

        //when
        try{
            mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location","/recipe/1/show"));
        } catch(Exception e){
            fail(e.getMessage());
        }
        //then
        verify(imageService,times(1)).saveImage(1L,mockMultipartFile);
    }

    @Test
    public void getImage(){
        //given
        byte[] imageBytes = "heywhatsup".getBytes();
        Byte[] boxedImageBytes = new Byte[imageBytes.length];
        int i = 0;
        for(byte b : imageBytes){
            boxedImageBytes[i++] = b;
        }
        RecipeCommand recipe = RecipeCommand.builder().id(1L).image(boxedImageBytes).build();
        when(recipeService.getRecipe(1L)).thenReturn(recipe);

        //when
        try{
            MockHttpServletResponse response =
                    mockMvc.perform(get("/recipe/1/image"))
                            .andExpect(status().isOk())
                            .andReturn().getResponse();

            byte[] returnedImage = response.getContentAsByteArray();

            //then
            assertEquals(imageBytes.length,returnedImage.length);
        } catch(Exception e){
            fail(e.getMessage());
        }

    }

}