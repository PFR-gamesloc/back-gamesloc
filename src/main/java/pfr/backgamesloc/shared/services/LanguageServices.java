package pfr.backgamesloc.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.DAL.entities.Language;
import pfr.backgamesloc.shared.repositories.LanguageRepository;

import java.util.List;

@Service
public class LanguageServices {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAll() {
        return (List<Language>) this.languageRepository.findAll();
    }

    public Language getLanguageById(Integer languageId) {
        return this.languageRepository.findByLanguageId(languageId);
    }
}
