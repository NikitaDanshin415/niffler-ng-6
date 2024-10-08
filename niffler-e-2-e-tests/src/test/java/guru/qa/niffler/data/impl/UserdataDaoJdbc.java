package guru.qa.niffler.data.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.UserdataDAO;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.entity.user.UserEntity;
import guru.qa.niffler.model.CurrencyValues;
import org.apache.kafka.common.protocol.types.Field;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class UserdataDaoJdbc implements UserdataDAO {
    private static Config CFG = Config.getInstance();

    @Override
    public UserEntity createUser(UserEntity user) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user( username, currency, firstname, surname, photo, photo_small, full_name)" +
                            "VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getCurrency().name());
                ps.setString(3, user.getFirstname());
                ps.setString(4, user.getSurname());
                ps.setObject(5, user.getPhoto());
                ps.setObject(6, user.getPhotoSmall());
                ps.setString(7, user.getFullname());
                ps.executeUpdate();

                final UUID generatedKey;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedKey = rs.getObject("id", UUID.class);
                    } else {
                        throw new SQLException("Can't find id in ResultSet");
                    }
                }
                user.setId(generatedKey);

                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE id = ?"
            )) {
                ps.setObject(1, id);
                ps.execute();

                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setUsername(rs.getObject("username", String.class));
                        userEntity.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                        userEntity.setFirstname(rs.getObject("firstname", String.class));
                        userEntity.setSurname(rs.getObject("surname", String.class));
                        userEntity.setPhoto(rs.getObject("photo", byte[].class));
                        userEntity.setPhotoSmall(rs.getObject("photo_small", byte[].class));
                        userEntity.setFullname(rs.getObject("full_name", String.class));

                        return Optional.of(userEntity);
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?"
            )) {
                ps.setObject(1, username);
                ps.execute();

                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setUsername(rs.getObject("username", String.class));
                        userEntity.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                        userEntity.setFirstname(rs.getObject("firstname", String.class));
                        userEntity.setSurname(rs.getObject("surname", String.class));
                        userEntity.setPhoto(rs.getObject("photo", byte[].class));
                        userEntity.setPhotoSmall(rs.getObject("photo_small", byte[].class));
                        userEntity.setFullname(rs.getObject("full_name", String.class));

                        return Optional.of(userEntity);
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(UserEntity user) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM spend where id = ?"
            )) {
                ps.setObject(1, user.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
