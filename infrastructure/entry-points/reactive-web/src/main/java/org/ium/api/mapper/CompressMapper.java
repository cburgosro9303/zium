package org.ium.api.mapper;

import org.ium.api.model.CompressedFileDto;
import org.ium.model.CompressedFile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompressMapper {

    CompressMapper INSTANCE = Mappers.getMapper(CompressMapper.class);

    CompressedFileDto toDto(CompressedFile file);
}
