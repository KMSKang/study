package com.spring.www.service.impl;

import com.spring.www.domain.entity.Resources;
import com.spring.www.repository.ResourcesRepository;
import com.spring.www.service.ResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired private ResourcesRepository resourcesRepository;

    @Transactional
    public Resources getResources(long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Transactional
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
    }

    @Transactional
    public void deleteResources(long id) {
        resourcesRepository.deleteById(id);
    }
}
