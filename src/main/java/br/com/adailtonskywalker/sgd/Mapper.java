package br.com.adailtonskywalker.sgd;

/**
 * Generic interface for mapping between an Entity, an Input DTO, and an Output DTO.
 *
 * @param <E> Represents the Entity type.
 * @param <I> Represents the Input DTO type.
 * @param <O> Represents the Output DTO type.
 */
public interface Mapper<E, I, O> {
    /**
     * Converts an Input DTO to an Entity.
     *
     * @param inputDto the Input DTO to be converted.
     * @return the converted Entity.
     */
    E toEntity(I inputDto);

    /**
     * Converts an Entity to an Output DTO.
     *
     * @param entity the Entity to be converted.
     * @return the converted Output DTO.
     */
    O toDto(E entity);
}
