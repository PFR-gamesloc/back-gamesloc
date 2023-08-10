package pfr.backgamesloc.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.DAL.entities.Type;
import pfr.backgamesloc.shared.repositories.TypeRepository;

import java.util.List;

@Service
public class TypeServices {
    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAll() {
        return (List<Type>) this.typeRepository.findAll();
    }

    public Type getTypeById(Integer typeId) {
        return this.typeRepository.findByTypeId(typeId);
    }
}
