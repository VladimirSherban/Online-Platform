package platform.mapper;

import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

public interface MapperSchema<A, B> {
    //маппим всё по общей схеме

    B toEntity(A dto);
    A toDto(B entity);
    List<A> toDto (Collection<B> entity);
    List<B> toEntity (Collection<A> dto);
}
