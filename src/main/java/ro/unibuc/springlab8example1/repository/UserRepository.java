package ro.unibuc.springlab8example1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        String saveUserSql = "INSERT INTO users (username, full_name, user_type, account_created) VALUES (?,?,?,?)";
        jdbcTemplate.update(saveUserSql, user.getUsername(), user.getFullName(), user.getUserType().name(), LocalDateTime.now());

        User savedUser = getUserWith(user.getUsername());
        UserDetails userDetails = user.getUserDetails();

        if (userDetails != null) {
            String saveUserDetailsSql = "INSERT INTO user_details (cnp, age, other_information) VALUES (?, ?, ?)";
            jdbcTemplate.update(saveUserDetailsSql, userDetails.getCnp(), userDetails.getAge(), userDetails.getOtherInformation());

            UserDetails savedUserDetails = getUserDetailsWith(userDetails.getCnp());
            savedUser.setUserDetails(savedUserDetails);

            String saveUsersUserDetails = "INSERT INTO users_user_details (users, user_details) VALUES (?, ?)";
            jdbcTemplate.update(saveUsersUserDetails, savedUser.getId(), savedUserDetails.getId());
        }

        return savedUser;
    }

    public User update(User userUpdated, String lastName) {

        Optional<User> user = Optional.ofNullable(get(lastName));

        if (user.isPresent()) {
            String updateUserSql = "UPDATE users SET username = ?, full_name = ?, user_type = ?, account_created = ? WHERE users.username = ?";
            jdbcTemplate.update(updateUserSql, userUpdated.getUsername(), userUpdated.getFullName(), userUpdated.getUserType().name(), userUpdated.getAccountCreated(), lastName);
            return user.get();
        }

        throw new UserNotFoundException("User not found");
    }

    public User delete(String lastName) {

        Optional<User> user = Optional.ofNullable(get(lastName));

        if (user.isPresent()) {
            String deleteDetailSql = "DELETE FROM users_user_details WHERE users = ?";
            jdbcTemplate.update(deleteDetailSql,user.get().getId());

            String deleteUserSql = "DELETE FROM users WHERE username = ?";
            jdbcTemplate.update(deleteUserSql,lastName);
            return user.get();
        }

        throw new UserNotFoundException("User not found");
    }

    public User get(String username) {
        // TODO : homework: use JOIN to fetch all details about the user
        return getUserWith(username);
    }

    private User getUserWith(String username) {
        String selectSql = "SELECT * FROM users u  JOIN user_details ud on u.id = ud.id WHERE u.username = ?";

        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userDetails(new UserDetails(resultSet.getLong("id"), resultSet.getString("cnp"),
                        (int) resultSet.getLong("age"), resultSet.getString("other_information")))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User not found");
    }

    private UserDetails getUserDetailsWith(String cnp) {
        String selectSql = "SELECT * from user_details WHERE user_details.cnp = ?";
        RowMapper<UserDetails> rowMapper = (resultSet, rowNo) -> UserDetails.builder()
                .id(resultSet.getLong("id"))
                .cnp(resultSet.getString("cnp"))
                .age(resultSet.getInt("age"))
                .otherInformation(resultSet.getString("other_information"))
                .build();

        List<UserDetails> details = jdbcTemplate.query(selectSql, rowMapper, cnp);

        if (!details.isEmpty()) {
            return details.get(0);
        }

        throw new UserNotFoundException("User details not found");
    }

    public List<User> getByType(String type) {
        String selectSql = "SELECT * FROM users u WHERE u.user_type = ?";

        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, type);

        if (!users.isEmpty()) {
            return users;
        }

        throw new UserNotFoundException("User not found");
    }
}
