package draen.dto;

import draen.domain.users.User;
import draen.domain.users.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserPublicDto implements Dto<User> {
    private String username;
    private Set<UserRole> roles;
}
