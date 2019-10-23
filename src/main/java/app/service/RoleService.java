package app.service;

import app.model.Role;

public interface RoleService {
    Role getById(int id);

    Role getRoleByName (String roleName);
}


