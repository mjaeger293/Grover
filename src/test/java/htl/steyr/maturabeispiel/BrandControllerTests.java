package htl.steyr.maturabeispiel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import htl.steyr.maturabeispiel.controllers.BrandController;
import htl.steyr.maturabeispiel.models.Brand;
import htl.steyr.maturabeispiel.models.User;
import htl.steyr.maturabeispiel.models.mapper.BrandMapper;
import htl.steyr.maturabeispiel.repositories.BrandRepository;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(BrandController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BrandControllerTests {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mockMvc;

    @MockBean
    BrandRepository brandRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    BrandMapper brandMapper;


    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        User admin = new User("admin@admin.at", "admin");
        admin.setToken("1234");
        admin.setTokenExpirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        when(userRepository.findFirstByEmailAndPassword("admin@admin.at", "admin")).thenReturn(admin);
        when(userRepository.existsByTokenAndTokenExpirationDateGreaterThan(anyString(), any(Date.class))).thenReturn(true);
        when(userRepository.findFirstByToken(anyString())).thenReturn(admin);

        Brand brand = new Brand("Apple");
        Brand brand2 = new Brand("HP");
        List<Brand> brands = new ArrayList<>(Arrays.asList(brand, brand2));
        when(brandRepository.findAll()).thenReturn(brands);

        //actualBrandMapper = new BrandMapper();
    }

    @Test
    void loadAllBrands() throws Exception {
        System.out.println(brandRepository.findAll().size());
        //System.out.println(actualBrandMapper.toDTO(new Brand("VW")));
        /*this.mockMvc.perform(get("/brand/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer 1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));*/

        mockMvc.perform(MockMvcRequestBuilders.get("/brand/load")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void createBeanTest() throws Exception {
        Brand brand = new Brand("Fujitsu");

        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        this.mockMvc.perform(post("/brand/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "1234")
                        .content(objectMapper.writeValueAsString(brandMapper.toDTO(brand))))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
