package com.spring.www.service.impl;

import com.spring.www.domain.entity.RoleHierarchy;
import com.spring.www.repository.RoleHierarchyRepository;
import com.spring.www.service.RoleHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    @Autowired private RoleHierarchyRepository roleHierarchyRepository;

    @Transactional
    @Override
    public String findAllHierarchy() {
        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuffer concatedRoles = new StringBuffer();
        while (itr.hasNext()) {
            RoleHierarchy model = itr.next();
            if (model.getParentName() != null) {
                concatedRoles.append(model.getParentName().getChildName());
                concatedRoles.append(" > ");
                concatedRoles.append(model.getChildName());
                concatedRoles.append("\n");
            }
        }
        return concatedRoles.toString();

    }
}
