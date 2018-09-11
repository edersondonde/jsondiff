package br.com.edonde.jsondiff.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.edonde.jsondiff.model.DiffElement;

/**
 * Repository of DiffElement.
 */
@Repository
public class DiffElementRepository {

    /**
     * Map of {@link DiffElement}.
     */
    @Autowired
    private Map<String, DiffElement> diffElements;

    /**
     * Saves a new instance of {@link DiffElement} if not present.
     *
     * @param entity The DiffElement to be saved.
     * @return The saved instance.
     */
    public DiffElement save(DiffElement entity) {
        diffElements.putIfAbsent(entity.getId(), entity);
        return entity;
    }

    /**
     * Searches for the {@link DiffElement} by Id.
     *
     * @param id The Id of the DiffElement.
     * @return An {@link Optional} containing the entity.
     */
    public Optional<DiffElement> findById(String id) {
        return Optional.ofNullable(diffElements.get(id));
    }
}
