package com.joumer.webservice.config;

import com.joumer.webservice.document.Role;
import com.joumer.webservice.document.User;
import com.joumer.webservice.logging.Log;
import com.joumer.webservice.repository.RoleRepository;
import com.joumer.webservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name= "application.db-init", havingValue = "true")
public class BootstrapDB implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MongoTemplate mongoTemplate;


    @Value("${application.db-init-checker:/tmp/.initDB}")
    private String checkerPath;

    @Value("${application.title}")
    private String appName;

    @Override
    public void run(ApplicationArguments args) {
        if (alreadyInitialized()) {
            Log.warn("Database already initialized, %s backend server has been started", appName);
            return;
        }
        Log.warn("Initializing database...");
        mongoTemplate.getDb().drop();
        var role1 = createRole("COMPANY_USER", "Standard User - Has no admin rights");
        var role2 = createRole("COMPANY_ADMIN_USER", "Company Admin User - Has permission to perform admin tasks on it's company");
        var role3 = createRole("ADMIN_USER", "Admin User - Has permission to perform admin tasks");
        createUser("Mustafa", "SAMISM", "mustafa.smesem@gmail.com", "1234" , true, Arrays.asList(role1, role2, role3));
        createUser("ANAS", "THALJEH", "anasthaljeh@gmail.com", "1234" , true, List.of(role1, role2, role3));
        Log.warn("%s backend server has been started", appName);
    }

    private Role createRole(String name, String description) {
        return roleRepository.save(new Role(name, description));
    }

    private void createUser(String name, String surname, String username, String password, boolean isAdmin, List<Role> roles) {
        var user = new User();
        user.setEnabled(true);
        user.setName(name);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setSurname(surname);
        user.setUsername(username);
        user.setAdmin(isAdmin);
        user.setRoles(roles);
        user = userRepository.save(user);
        Log.warn("- Create user [%s %s]", user.getName(), user.getSurname());
    }


    private boolean alreadyInitialized() {
        try {
            File myObj = new File(checkerPath);
            if (!myObj.createNewFile()) return true;
        } catch (IOException e) {
            Log.error("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

}
