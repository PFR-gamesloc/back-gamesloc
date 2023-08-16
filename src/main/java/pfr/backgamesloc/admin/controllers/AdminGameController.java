package pfr.backgamesloc.admin.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pfr.backgamesloc.games.DAL.entities.*;
import pfr.backgamesloc.games.controllers.DTO.*;
import pfr.backgamesloc.games.services.GameService;
import pfr.backgamesloc.shared.services.EditorServices;
import pfr.backgamesloc.shared.services.LanguageServices;
import pfr.backgamesloc.shared.services.TagServices;
import pfr.backgamesloc.shared.services.TypeServices;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/game")
@RequiredArgsConstructor

public class AdminGameController {
    private final GameService gameService;

    private final EditorServices editorServices;

    private final TypeServices typeServices;

    private final LanguageServices languageServices;

    private final TagServices tagServices;

    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public List<GameDTO> getAll() {
        List<Game> games = this.gameService.getAll();
        List<GameDTO> gameDTOS = new ArrayList<>();
        for (Game game : games) {
            gameDTOS.add(this.transformGameTOGameDTO(game));
        }
        return gameDTOS;
    }

    public GameDTO transformGameTOGameDTO(Game game) {
        return this.modelMapper.map(game, GameDTO.class);
    }

    @GetMapping("/editors")
    public List<EditorDTO> allTheEditors() {
        List<Editor> editors = this.editorServices.getAll();
        List<EditorDTO> editorDTOList = new ArrayList<>();
        for (Editor editor : editors) {
            editorDTOList.add(this.transformEditorTOEditorDTO(editor));
        }
        return editorDTOList;
    }

    public EditorDTO transformEditorTOEditorDTO(Editor editor) {
        return this.modelMapper.map(editor, EditorDTO.class);
    }

    @GetMapping("/languages")
    public List<LanguageDTO> allTheLanguages() {
        List<Language> languages = this.languageServices.getAll();
        List<LanguageDTO> languageDTOList = new ArrayList<>();
        for (Language language : languages) {
            languageDTOList.add(this.transformLanguageTOLanguageDTO(language));
        }
        return languageDTOList;
    }

    public LanguageDTO transformLanguageTOLanguageDTO(Language language) {
        return this.modelMapper.map(language, LanguageDTO.class);
    }

    @GetMapping("/tags")
    public List<TagDTO> allTheTags() {
        List<Tag> tags = this.tagServices.getAll();
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTOList.add(this.transformTagTOTagDTO(tag));
        }
        return tagDTOList;
    }

    public TagDTO transformTagTOTagDTO(Tag tag) {
        return this.modelMapper.map(tag, TagDTO.class);
    }

    @GetMapping("/types")
    public List<TypeDTO> allTheTypes() {
        List<Type> types = this.typeServices.getAll();
        List<TypeDTO> typeDTOList = new ArrayList<>();
        for (Type type : types) {
            typeDTOList.add(this.transformeTypeTOTypeDTO(type));
        }
        return typeDTOList;
    }

    public TypeDTO transformeTypeTOTypeDTO(Type type) {
        return this.modelMapper.map(type, TypeDTO.class);
    }

    @PostMapping("/add")
    public ResponseEntity<Game> addAGame(@RequestBody GameEditDTO gameEditDTO) {
        Game game = processGameEditDTO(gameEditDTO);
        this.gameService.createANewGame(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Game> editAGame(@PathVariable("id") Integer id, @RequestBody GameEditDTO gameEditDTO) {
        Game game = processGameEditDTO(gameEditDTO);
        game.setGameId(id);
        this.gameService.editGameById(id, game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    private Game processGameEditDTO(GameEditDTO gameEditDTO) {
        Game game = this.modelMapper.map(gameEditDTO, Game.class);

        game.setType(typeServices.getTypeById(gameEditDTO.getTypeId()));
        game.setEditor(editorServices.getEditorById(gameEditDTO.getEditorId()));

        List<Language> languages = new ArrayList<>();
        for (Integer languageId : gameEditDTO.getLanguages()) {
            languages.add(languageServices.getLanguageById(languageId));
        }
        game.setLanguages(languages);

        List<Tag> tags = new ArrayList<>();
        for (Integer tagId : gameEditDTO.getTags()) {
            tags.add(tagServices.getTagById(tagId));
        }
        game.setTags(tags);

        return game;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAGame(@PathVariable("id") Integer id) {
        this.gameService.deleteById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            String fileName = imageFile.getOriginalFilename();
            File uploadPath = new File("/assets/img"); // Remplacez ce chemin par le chemin absolu vers votre dossier de destination
            File targetFile = new File(uploadPath, fileName); // Utilisez le constructeur File avec un répertoire parent et un nom de fichier

            imageFile.transferTo(targetFile);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload image");
        }
    }


}
