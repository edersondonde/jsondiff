package br.com.edonde.jsondiff.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.edonde.jsondiff.model.DiffElement;

/**
 * Configuration class for the {@link DiffElement} map
 */
@Configuration
public class DiffElementMap {

    /**
     * Creates and returns a new instance of the {@link DiffElement} map.
     *
     * @return A new instance of DiffElement Map
     */
    @Bean
    public Map<String, DiffElement> diffElementsBean () {
        Map<String, DiffElement> map = new HashMap<>();
        return map;
    }

}
