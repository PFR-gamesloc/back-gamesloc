package pfr.backgamesloc.security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.games.DAL.entities.*;
import pfr.backgamesloc.games.controllers.DTO.*;
import pfr.backgamesloc.games.services.GameService;
import pfr.backgamesloc.security.Service.AuthService;
import pfr.backgamesloc.security.Service.TokenService;
import pfr.backgamesloc.security.controllers.DTO.AuthRequest;
import pfr.backgamesloc.security.controllers.DTO.RegisterRequest;
import pfr.backgamesloc.security.controllers.DTO.Token;
import pfr.backgamesloc.shared.services.EditorServices;
import pfr.backgamesloc.shared.services.LanguageServices;
import pfr.backgamesloc.shared.services.TagServices;
import pfr.backgamesloc.shared.services.TypeServices;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/auth")
@DependsOn("securityFilterChain")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final TokenService tokenService;

    private final CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<Token> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Token> token(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
        String jwt = tokenService.getJwt(request.getHeader("Authorization"));
        String userEmail = tokenService.extractUsername(jwt);
        UserDetails user = customerService.loadUserByUsername(userEmail);
        return ResponseEntity.ok(tokenService.isTokenValid(jwt, user));
    }


    //    Partie à déplacer

    private final EditorServices editorServices;

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

    private final LanguageServices languageServices;

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

    private final TagServices tagServices;

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

    private final TypeServices typeServices;

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

    private final GameService gameService;
    @PostMapping("/game/add")
    public ResponseEntity<Game> addAGame(@RequestBody GameEditDTO gameEditDTO) {
        if (gameEditDTO.getGameName() == null || gameEditDTO.getGameName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le jeu n'a pas pu être créé");
        }

        Game game = this.modelMapper.map(gameEditDTO, Game.class);

        game.setType(typeServices.getTypeById(gameEditDTO.getTypeId()));
        game.setEditor(editorServices.getEditorById(gameEditDTO.getEditorId()));

        List<Language> languages = new ArrayList<>();
        for (Integer id : gameEditDTO.getLanguages()) {
            languages.add(languageServices.getLanguageById(id));
        }

        game.setLanguages(languages);

        List<Tag> tags = new ArrayList<>();
        for (Integer id : gameEditDTO.getTags()) {
            tags.add(tagServices.getTagById(id));
        }

        game.setTags(tags);

        System.out.println(game.getEditor().getName());

        this.gameService.createANewGame(game);

        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

}

