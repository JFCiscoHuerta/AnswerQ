package com.gklyphon.AnswerQ.mapper;

import com.gklyphon.AnswerQ.dtos.RegisterUserDto;
import com.gklyphon.AnswerQ.dtos.ResponseUserDto;
import com.gklyphon.AnswerQ.models.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper interface for converting entities.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-21
 */
@Mapper(componentModel = "spring")
public interface IMapper {

    /**
     * Converts a {@link ResponseUserDto} to a {@link User} entity.
     *
     * @param responseUserDto The object to be converted
     * @return The object converted
     */
    @Mapping(target = "password", ignore = true)
    User fromUserDtoToUser(ResponseUserDto responseUserDto);
    User fromUserRegisterUserDtoToUser(RegisterUserDto registerUserDto);
    /**
     * Converts a {@link User} to a {@link ResponseUserDto} entity.
     *
     * @param user The object to be converted
     * @return The object converted
     */
    @InheritInverseConfiguration
    ResponseUserDto fromUserToUserDto(User user);
    RegisterUserDto fromUserToRegisterUserDto(User user);
}
