package br.com.edonde.jsondiff.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.edonde.jsondiff.model.DiffElement;

public class DiffElementRepositoryTest {

    @InjectMocks
    private DiffElementRepository repository;

    @Spy
    private Map<String, DiffElement> diffElements;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));
        DiffElement entity = new DiffElement(id);
        repository.save(entity);
        verify(diffElements).putIfAbsent(id, entity);
    }

    @Test
    public void testFindById() {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));
        DiffElement entity = new DiffElement(id);
        when(diffElements.get(id)).thenReturn(entity);

        Optional<DiffElement> result = repository.findById(id);
        assertThat(result.get()).isSameAs(entity);
    }

    @Test
    public void testFindByIdNotFound() {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));
        when(diffElements.get(id)).thenReturn(null);

        Optional<DiffElement> result = repository.findById(id);
        assertThat(result.isPresent()).isFalse();
    }
}
