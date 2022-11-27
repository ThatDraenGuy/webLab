package draen.rest.modelassemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import draen.dto.UserGetDto;
import draen.rest.controllers.UserAttemptsController;
import draen.rest.controllers.UserController;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserGetDtoModelAssembler implements RepresentationModelAssembler<UserGetDto, EntityModel<UserGetDto>> {
    @Override
    public @NonNull EntityModel<UserGetDto> toModel(@NonNull UserGetDto entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UserController.class).userById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).userByUsername(entity.getUsername())).withSelfRel(),
                linkTo(methodOn(UserAttemptsController.class).allAttempts(entity.getId())).withRel("attempts")
                );
    }
}
