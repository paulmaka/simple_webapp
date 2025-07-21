package com.simplespring.webapp.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepository_CheckSaved() {

        //Arrange
        User user = new User(null, "Lessi", "les@gmail.com", LocalDate.of(1992, 11, 27), null);

        //Act
        User saveUser = userRepository.save(user);

        //Assert
        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser.getId()).isGreaterThan(0);
        Assertions.assertThat(userRepository.findById(saveUser.getId())).isPresent();
        Assertions.assertThat(userRepository.findById(saveUser.getId()).get().getId()).isEqualTo(user.getId());
        
    }

    @Test
    public void userRepository_FindByEmail_ReturnUser() {
        // Arrange
        User user = new User(null,
                            "Lessi",
                            "less@gmail.com",
                            LocalDate.of(1992, 11, 27),
                            null);

        // Act
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        // Assert
        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundUser.get().getName()).isEqualTo(user.getName());
    }

}
