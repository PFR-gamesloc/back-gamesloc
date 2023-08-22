package pfr.backgamesloc.shared.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.dal.entities.Language;
import pfr.backgamesloc.shared.repositories.LanguageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServices {

    private final LanguageRepository languageRepository;

    public List<Language> getAll() {
        return (List<Language>) this.languageRepository.findAll();
    }

    public Language getLanguageById(Integer languageId) {
        return this.languageRepository.findById(languageId)
                .orElseThrow(()->new EntityNotFoundException("this language doesn't exist"));
    }
}
