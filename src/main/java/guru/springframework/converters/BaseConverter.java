package guru.springframework.converters;

import guru.springframework.domain.BaseEntity;

public interface BaseConverter<T extends BaseEntity,D> {
    D convertEntityToCommand(T entity);
    T convertCommandToEntity(D command);
}
