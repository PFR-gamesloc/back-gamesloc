package pfr.backgamesloc.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pfr.backgamesloc.admin.controllers.DTO.GameEditRequest;
import pfr.backgamesloc.admin.controllers.DTO.ResponseMessage;
import pfr.backgamesloc.admin.services.FileStorageService;
import pfr.backgamesloc.games.DAL.entities.*;
import pfr.backgamesloc.games.controllers.DTO.*;
import pfr.backgamesloc.games.services.GameService;
import pfr.backgamesloc.shared.services.EditorServices;
import pfr.backgamesloc.shared.services.LanguageServices;
import pfr.backgamesloc.shared.services.TagServices;
import pfr.backgamesloc.shared.services.TypeServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final FileStorageService storageService;


    @GetMapping("/{id}")
    public GameEditRequest gameToEdit(@PathVariable("id") Integer id){
         Game game = this.gameService.getGameById(id);
         GameEditRequest gameEditRequest = this.modelMapper.map(game, GameEditRequest.class);

         List<Integer> GameEditDtoIds = new ArrayList<>();
         for (Language language : game.getLanguages()){
             GameEditDtoIds.add(language.getLanguageId());
         }
         gameEditRequest.setLanguagesId(GameEditDtoIds);

        GameEditDtoIds.clear();
        for (Tag tag : game.getTags()){
            GameEditDtoIds.add(tag.getTagId());
        }
        gameEditRequest.setTagsId(GameEditDtoIds);
        return gameEditRequest;

    }

    @GetMapping("/editors")
    public List<EditorDTO> allTheEditors() {
        List<Editor> editors = this.editorServices.getAll();
        List<EditorDTO> editorDTOList = new ArrayList<>();
        for (Editor editor : editors) {
            editorDTOList.add(this.modelMapper.map(editor, EditorDTO.class));
        }
        return editorDTOList;
    }

    @GetMapping("/languages")
    public List<LanguageDTO> allTheLanguages() {
        List<Language> languages = this.languageServices.getAll();
        List<LanguageDTO> languageDTOList = new ArrayList<>();
        for (Language language : languages) {
            languageDTOList.add(this.modelMapper.map(language, LanguageDTO.class));
        }
        return languageDTOList;
    }

    @GetMapping("/tags")
    public List<TagDTO> allTheTags() {
        List<Tag> tags = this.tagServices.getAll();
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTOList.add(this.modelMapper.map(tag, TagDTO.class));
        }
        return tagDTOList;
    }


    @GetMapping("/types")
    public List<TypeDTO> allTheTypes() {
        List<Type> types = this.typeServices.getAll();
        List<TypeDTO> typeDTOList = new ArrayList<>();
        for (Type type : types) {
            typeDTOList.add(this.modelMapper.map(type, TypeDTO.class));
        }
        return typeDTOList;
    }

    @PostMapping("/add")
    public ResponseEntity<GameEditRequest> addAGame(@RequestBody GameEditRequest gameEditRequest) {
        Game game = this.processGameEditDTO(gameEditRequest);
        this.gameService.createANewGame(game);
        return new ResponseEntity<>(this.modelMapper.map(game, GameEditRequest.class), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<GameEditRequest> editAGame(@PathVariable("id") Integer id, @RequestBody GameEditRequest gameEditRequest) {
        Game game = processGameEditDTO(gameEditRequest);
        game.setGameId(id);
        this.gameService.editGameById(id, game);
        return new ResponseEntity<>(this.modelMapper.map(game, GameEditRequest.class), HttpStatus.CREATED);
    }

    private Game processGameEditDTO(GameEditRequest gameEditRequest) {
        Game game = this.modelMapper.map(gameEditRequest, Game.class);

        game.setType(typeServices.getTypeById(gameEditRequest.getTypeId()));
        game.setEditor(editorServices.getEditorById(gameEditRequest.getEditorId()));

        List<Language> languages = new ArrayList<>();
        for (Integer languageId : gameEditRequest.getLanguagesId()) {
            languages.add(languageServices.getLanguageById(languageId));
        }
        game.setLanguages(languages);

        List<Tag> tags = new ArrayList<>();
        for (Integer tagId : gameEditRequest.getTagsId()) {
            tags.add(tagServices.getTagById(tagId));
        }
        game.setTags(tags);

        return game;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteAGame(@PathVariable("id") Integer id) {
        Boolean isDeleted = this.gameService.deleteById(id);
        return new ResponseEntity<>(isDeleted, HttpStatus.ACCEPTED);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("File is empty or not provided."));
            }

            String originalFilename = file.getOriginalFilename();
            Path existingFilePath = Paths.get("uploads", originalFilename);

            Files.deleteIfExists(existingFilePath);

            storageService.save(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Uploaded the file successfully: " + originalFilename));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage("Could not upload the file: " + e.getMessage()));
        }
    }
}
