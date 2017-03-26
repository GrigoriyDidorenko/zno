package ua.com.zno.online.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by quento on 26.03.17.
 */
@Component
public class Beans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
