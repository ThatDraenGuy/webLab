package draen.rest.controllers.authorized;

import draen.components.AreaShooterComponent;
import draen.domain.attempts.CoordInfo;
import draen.domain.users.User;
import draen.domain.users.UserAttemptInfo;
import draen.rest.Wrapper;
import draen.dto.attempt.UserAttemptInfoDto;
import draen.dto.attempt.CoordInfoDto;
import draen.exceptions.DtoException;
import draen.rest.controllers.UserControllerUtils;
import draen.security.AppUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/areacheck")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AreaCheckController {
    private final AreaShooterComponent areaShooterComponent;
    private final UserControllerUtils userUtils;
    private final Wrapper wrapper;

    @PostMapping(value = "/shoot", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UserAttemptInfoDto> shoot(@RequestBody CoordInfoDto coordInfoDto, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        try {
            User user = userUtils.getUser(appUserDetails.getUsername());
            CoordInfo coordInfo = wrapper.unwrap(coordInfoDto, CoordInfo.class);
            UserAttemptInfo attemptInfo = areaShooterComponent.shoot(coordInfo, user);
            return wrapper.wrap(attemptInfo, UserAttemptInfoDto.class);
        } catch (DtoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}