package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserdataDAO {
    UserEntity createUser(UserEntity user);
    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findUserByUsername(String username);
    void deleteUser(UserEntity user);
}
