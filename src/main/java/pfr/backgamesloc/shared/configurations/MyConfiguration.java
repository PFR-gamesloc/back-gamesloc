package pfr.backgamesloc.shared.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;

@Configuration
public class MyConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
