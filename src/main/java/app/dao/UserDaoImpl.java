package app.dao;

import app.model.Role;
import app.model.User;
import app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao{
    private EntityManager entityManager;

    @Autowired
    public  void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    private RoleService roleService;

    public List<User> getAllUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public void addUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        user.setRoles(getRoleSet(user));
        entityManager.persist(user);
    }

    private Set<Role> getRoleSet(User user) {
        Role roleAdmin = roleService.getById(1);
        Role roleUser = roleService.getById(2);
        Set<Role> roleSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ROLE_ADMIN")) {
                roleSet.add(roleAdmin);
            }
            if (role.getRole().equals("ROLE_USER")) {
                roleSet.add(roleUser);
            }
        }
        return roleSet;
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUser(Integer id) {
        return  entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        user.setRoles(getRoleSet(user));
        entityManager.merge(user);
    }

    @Override
    public User getUserByLogin(String login) {
        User user1 = null;
        List<User> userList = entityManager.createQuery("from User").getResultList();
        for(User user : userList){
            if(user.getName().equals(login)){
                user1 = user;
            }
        }
        return user1;
    }

}
