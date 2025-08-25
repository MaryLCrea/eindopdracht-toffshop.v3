package toff.novi.eindopdrachttoffshop.controllers;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toff.novi.eindopdrachttoffshop.dtos.RoleDto;
import toff.novi.eindopdrachttoffshop.models.Role;
import toff.novi.eindopdrachttoffshop.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {

    private final RoleRepository repos;

    public RoleController(RoleRepository repos) {
        this.repos = repos;
    }
    @GetMapping("/roles")
    public List<RoleDto> getRoles() {
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role r : repos.findAll()) {
            RoleDto rdto = new RoleDto();
            rdto.rolename = r.getRolename();
            roleDtos.add(rdto);
        }
        return roleDtos;
    }
}
