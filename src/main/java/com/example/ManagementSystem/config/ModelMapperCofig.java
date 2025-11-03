package com.example.ManagementSystem.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ManagementSystem.dto.UserDTO;
import com.example.ManagementSystem.model.User;

@Configuration
public class ModelMapperCofig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modoMapper = new ModelMapper();
        modoMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        modoMapper.typeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setTransactions));
        
        return modoMapper;

    }
}
