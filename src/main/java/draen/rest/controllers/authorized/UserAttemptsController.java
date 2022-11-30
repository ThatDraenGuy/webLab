package draen.rest.controllers.authorized;


import draen.components.AreaShooterComponent;
import draen.domain.attempts.CoordInfo;
import draen.domain.users.User;
import draen.domain.users.UserAttemptInfo;
import draen.rest.Wrapper;
import draen.dto.attempt.UserAttemptInfoDto;
import draen.dto.attempt.CoordInfoDto;
import draen.exceptions.DtoException;
import draen.exceptions.UserIdNotFoundException;
import draen.rest.controllers.UserControllerUtils;
import draen.storage.UserAttemptInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users/id/{userId}/attempts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAttemptsController {
    private final AreaShooterComponent areaShooterComponent;
    private final UserAttemptInfoRepository repository;
    private final UserControllerUtils utils;
    private final Wrapper wrapper;


    @GetMapping
    public CollectionModel<EntityModel<UserAttemptInfoDto>> allAttempts(@PathVariable long userId) {
        return wrapper.wrapAll(repository.findByUserIdEquals(userId), UserAttemptInfoDto.class);
    }

    @GetMapping("/{attemptId}")
    public EntityModel<UserAttemptInfoDto> oneAttempt(@PathVariable long userId, @PathVariable long attemptId) {
        return wrapper.wrap(repository.findByIdAndUserIdEquals(userId, attemptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "attempt not found")),
                UserAttemptInfoDto.class);
    }

    @PostMapping("/shoot")
    public EntityModel<UserAttemptInfoDto> shoot(@RequestBody CoordInfoDto coordInfoDto, @PathVariable long userId) {
        try {
            User user = utils.getUser(userId);
            CoordInfo coordInfo = wrapper.unwrap(coordInfoDto, CoordInfo.class);
            UserAttemptInfo attemptInfo = areaShooterComponent.shoot(coordInfo, user);
            return wrapper.wrap(attemptInfo, UserAttemptInfoDto.class);
        } catch (UserIdNotFoundException | DtoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}