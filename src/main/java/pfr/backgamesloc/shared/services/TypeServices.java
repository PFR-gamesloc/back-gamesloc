package pfr.backgamesloc.shared.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.dal.entities.Type;
import pfr.backgamesloc.shared.repositories.TypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServices {

    private final TypeRepository typeRepository;

    public List<Type> getAll() {
        return (List<Type>) this.typeRepository.findAll();
    }

    public Type getTypeById(Integer typeId) {
        return this.typeRepository.findById(typeId).orElseThrow(()->new EntityNotFoundException("this type doesn't exist"));
    }
}
