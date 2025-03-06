package com.fgr.apirest.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.ItemDTO;
import com.fgr.apirest.entity.Item;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemDTOConverter {

    private final ModelMapper modelMapper;

    public ItemDTO convertToDto(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    public Item convertToEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }
}
