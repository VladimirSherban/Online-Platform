package platform.api;

import platform.dto.LoginReqDto;
import platform.dto.RegReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@Validated
@Tag(name = "Auth", description = "the Auth API")
public interface AuthApi {

    @Operation(operationId = "loginUsingPOST", summary = "login", tags = { "Auth" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Authentication.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(value = "/login", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<Authentication> loginUsingPOST(
            @Parameter(name = "req", description = "req", required = true) @Valid @RequestBody LoginReqDto req);

    @Operation(operationId = "registerUsingPOST", summary = "register", tags = { "Auth" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PostMapping(value = "/register", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<Object> registerUsingPOST(
            @Parameter(name = "req", description = "req", required = true) @Valid @RequestBody RegReqDto req);
}
