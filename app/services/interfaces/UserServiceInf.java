package services.interfaces;

import datamodels.dto.LoginDTO;
import datamodels.dto.UserDTO;
import datamodels.entities.User;

import java.util.Optional;

public interface UserServiceInf {

    Optional<UserDTO> findUserByUsername(String username);

    Optional<User> findUserEntityByUsername(String username);

    Optional<UserDTO> authenticateUser(LoginDTO loginDTO);

    Optional<UserDTO> saveUser(UserDTO userDTO);

}
