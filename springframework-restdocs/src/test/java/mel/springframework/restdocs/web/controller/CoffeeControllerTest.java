package mel.springframework.restdocs.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mel.springframework.restdocs.domain.Coffee;
import mel.springframework.restdocs.repositories.CoffeeRepository;
import mel.springframework.restdocs.web.model.CoffeeDto;
import mel.springframework.restdocs.web.model.CoffeeStyleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static  org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(CoffeeController.class)
@ComponentScan(basePackages = "mel.springframework.restdocs.web.mappers")
public class CoffeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CoffeeRepository coffeeRepository;

    @Test
    void getCoffeeById() throws Exception {
        given(coffeeRepository.findById(any())).willReturn(Optional.of(Coffee.builder().build()));

        mockMvc.perform(get("/api/v1/coffee/{coffeeId}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andDo(document("v1/coffee", pathParameters(
                parameterWithName("coffeeId").description("UUID of desired coffee to get.")
        )));
    }

    @Test
    void saveNewCoffee() throws Exception {
        CoffeeDto coffeeDto =  getValidCoffeeDto();
        String coffeeDtoJson = objectMapper.writeValueAsString(coffeeDto);

        mockMvc.perform(post("/api/v1/coffee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(coffeeDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCoffeeById() throws Exception {
        CoffeeDto coffeeDto =  getValidCoffeeDto();
        String coffeeDtoJson = objectMapper.writeValueAsString(coffeeDto);

        mockMvc.perform(put("/api/v1/coffee/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(coffeeDtoJson))
                .andExpect(status().isNoContent());
    }

    CoffeeDto getValidCoffeeDto(){
        return CoffeeDto.builder()
                .coffeeName("White Flat")
                .coffeeStyle(CoffeeStyleEnum.ARABIACA)
                .price(new BigDecimal("9.99"))
                .upc(123123123123L)
                .build();

    }

}