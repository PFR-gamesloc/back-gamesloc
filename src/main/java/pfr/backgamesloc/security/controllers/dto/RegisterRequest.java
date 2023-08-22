package pfr.backgamesloc.security.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Size(min = 2, max = 50)
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Zàáâäãåçèéêëìíîïñòóôöõøùúûüýÿ'\\-\\s]{2,}")
    private String firstName;

    @Size(min = 2, max = 50)
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Zàáâäãåçèéêëìíîïñòóôöõøùúûüýÿ'\\-\\s]{2,}")
    private String lastName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&^*,;])[A-Za-z\\d@$!%?&^*,;]{8,}")
    private String password;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    @Pattern(regexp = "[0-9]{10}")
    private String phoneNumber;

    @NotNull
    private Integer numberAddress;

    @Pattern(regexp = "\\s?(?:BIS|TER|QUARTER)?")
    private String complementaryNumber;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ]['a-zA-ZÀ-ÖØ-öø-ÿ0-9\\s,'’\\-]*")
    private String streetName;

    private String complementaryAddress;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?:\\d{5}|2A\\d{3}|2B\\d{3})")
    private String postalCode;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ\\s'\\-]*")
    private String cityName;

}
