package com.fgr.apirest.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.UserDTO;
import com.fgr.apirest.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDTOConverter {

    private final ModelMapper modelMapper;

    public UserDTO convertirADTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}


