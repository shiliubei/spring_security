package app.dao;

import app.model.Role;

public interface RoleDao {
    Role getById(int id);
    Role getRoleByName (String roleName);
}

