package mel.springframework.restdocs.web.controller;
import lombok.RequiredArgsConstructor;
import mel.springframework.restdocs.repositories.CoffeeRepository;
import mel.springframework.restdocs.web.mappers.CoffeeMapper;
import mel.springframework.restdocs.web.model.CoffeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-05-12.
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/coffee")
@RestController
public class CoffeeController {

    private final CoffeeMapper coffeeMapper;
    private final CoffeeRepository coffeeRepository;

    @GetMapping("/{coffeeId}")
    public ResponseEntity<CoffeeDto> getCoffeeById(@PathVariable("coffeeId") UUID coffeeId){

        return new ResponseEntity<>(coffeeMapper.CoffeeToCoffeeDto(coffeeRepository.findById(coffeeId).get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewCoffee(@RequestBody @Validated CoffeeDto coffeeDto){

        coffeeRepository.save(coffeeMapper.CoffeeDtoToCoffee(coffeeDto));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{coffeeId}")
    public ResponseEntity updateCoffeeById(@PathVariable("coffeeId") UUID coffeeId, @RequestBody @Validated CoffeeDto coffeeDto){
        coffeeRepository.findById(coffeeId).ifPresent(coffee -> {
            coffee.setCoffeeName(coffeeDto.getCoffeeName());
            coffee.setCoffeeStyle(coffeeDto.getCoffeeStyle().name());
            coffee.setPrice(coffeeDto.getPrice());
            coffee.setUpc(coffeeDto.getUpc());

            coffeeRepository.save(coffee);
        });

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}