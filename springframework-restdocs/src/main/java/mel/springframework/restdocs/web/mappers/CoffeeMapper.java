package mel.springframework.restdocs.web.mappers;

import mel.springframework.restdocs.domain.Coffee;
import mel.springframework.restdocs.web.model.CoffeeDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CoffeeMapper 
{
    CoffeeDto CoffeeToCoffeeDto(Coffee beer);

    Coffee CoffeeDtoToCoffee(CoffeeDto dto);
}
