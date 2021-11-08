package mel.springframework.restdocs.bootstrap;

import mel.springframework.restdocs.domain.Coffee;
import mel.springframework.restdocs.repositories.CoffeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CoffeeInitializer implements CommandLineRunner
{
    private final CoffeeRepository coffeeRepository;

    public CoffeeInitializer(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCoffeeObjects();
    }

    private void loadCoffeeObjects() {
        if(coffeeRepository.count() == 0){

            coffeeRepository.save(Coffee.builder()
                    .coffeeName("Espresso")
                    .coffeeStyle("LIBERICA")
                    .minOnHand(12)
                    .upc(337010000001L)
                    .price(new BigDecimal("12.95"))
                    .build());

            coffeeRepository.save(Coffee.builder()
                    .coffeeName("Cappucino")
                    .coffeeStyle("ROBUSTA")
                    .minOnHand(12)
                    .upc(337010000002L)
                    .price(new BigDecimal("11.95"))
                    .build());
        }
    }
}
