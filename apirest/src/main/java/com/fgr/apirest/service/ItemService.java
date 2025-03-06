package com.fgr.apirest.service;

import com.fgr.apirest.dto.ItemDTO;
import com.fgr.apirest.entity.Item;
import com.fgr.apirest.entity.Prueba;
import com.fgr.apirest.exception.RecursoNoEncontradoException;
import com.fgr.apirest.repository.ItemRepository;
import com.fgr.apirest.repository.PruebaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final PruebaRepository pruebaRepository;
    private final PruebaService pruebaService;

    public ItemService(ItemRepository itemRepository, PruebaRepository pruebaRepository, PruebaService pruebaService) {
        this.itemRepository = itemRepository;
        this.pruebaRepository = pruebaRepository;
        this.pruebaService = pruebaService;
    }

    

    // Añadir un nuevo ítem
    public Optional<ItemDTO> save(ItemDTO itemDTO) {
        Integer pruebaId = itemDTO.getPruebaId();

        if (pruebaId == null) {
            Optional<Integer> lastPruebaId = pruebaService.findLastPruebaId();
            if (lastPruebaId.isEmpty()) {
                return Optional.empty(); 
            }
            pruebaId = lastPruebaId.get();
        }

        Optional<Prueba> pruebaOptional = pruebaRepository.findById(pruebaId);
        if (pruebaOptional.isEmpty()) {
            return Optional.empty();
        }

        Prueba prueba = pruebaOptional.get();
        Item item = new Item(null, itemDTO.getDescripcion(), itemDTO.getPeso(), itemDTO.getGradosConsecucion(), prueba, null);
        Item savedItem = itemRepository.save(item);

        return Optional.of(convertToDTO(savedItem));
    }

    // Traer todos los ítems
    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Traer un ítem por ID
    public ItemDTO findById(Integer idItem) {
        return itemRepository.findById(idItem)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ítem con ID " + idItem + " no encontrado"));
    }

    // Actualizar un ítem
    public Optional<ItemDTO> update(Integer idItem, ItemDTO itemDTO) {
        return itemRepository.findById(idItem).flatMap(existingItem -> {
            Optional<Prueba> pruebaOptional = pruebaRepository.findById(itemDTO.getPruebaId());

            if (pruebaOptional.isEmpty()) {
                return Optional.empty();
            }

            Prueba prueba = pruebaOptional.get();
            existingItem.setDescripcion(itemDTO.getDescripcion());
            existingItem.setPeso(itemDTO.getPeso());
            existingItem.setGradosConsecucion(itemDTO.getGradosConsecucion());
            existingItem.setPrueba(prueba);

            Item updatedItem = itemRepository.save(existingItem);

            return Optional.of(convertToDTO(updatedItem));
        });
    }

    // Eliminar un ítem
    public boolean delete(Integer idItem) {
        if (itemRepository.existsById(idItem)) {
            itemRepository.deleteById(idItem);
            return true;
        }
        return false;
    }

    // Traer todos los ítems de una prueba específica
    public List<ItemDTO> findByPruebaId(Integer idPrueba) {
        return itemRepository.findByPrueba_IdPrueba(idPrueba).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // metodo privado para convertir un Item a ItemDTO
    private ItemDTO convertToDTO(Item item) {
        return new ItemDTO(
                item.getIdItem(),
                item.getDescripcion(),
                item.getPeso(),
                item.getGradosConsecucion(),
                item.getPrueba().getIdPrueba()
        );
    }
}
