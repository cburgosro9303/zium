package org.ium.api.mapper;

import org.ium.api.model.CompressedFileDto;
import org.ium.api.model.FileToCompressDto;
import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompressMapper {


    CompressMapper INSTANCE = Mappers.getMapper(CompressMapper.class);


    FileToCompress toModel(FileToCompressDto dto);

    CompressedFileDto toDto(CompressedFile compressedFile);

}
