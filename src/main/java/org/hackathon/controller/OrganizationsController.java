package org.hackathon.controller;

import org.hackathon.dto.OrganisationSignupDto;
import org.hackathon.entity.Event;
import org.hackathon.entity.Organisation;
import org.hackathon.mapper.OrganizationMapper;
import org.hackathon.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationsController {


    OrganizationRepository organizationRepository;
    OrganizationMapper organizationMapper;

    @Autowired
    OrganizationsController(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @GetMapping()
    ResponseEntity<Resources<?>> getAllActiveOrganizations(@RequestParam(required = false) Boolean confirmed) {
        Iterable<Organisation> active = organizationRepository.findByConfirmed(confirmed == null ? true : confirmed);
        return ResponseEntity.ok(getResource(active, Organisation.class));
    }

    @GetMapping("/{id}/events")
    ResponseEntity<Resources<?>> getOrganizationEvents(@PathVariable("id") @Positive @NotNull Long id) {
        Optional<Organisation> active = organizationRepository.findById(id);

        if (!active.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(getResource(active.get().getEvents(), Event.class));
    }

    @PutMapping()
    ResponseEntity<Resources<?>> updateOrganization(@RequestBody OrganisationSignupDto organisationSignupDto) {
        organizationRepository.save(organizationMapper.toEntity(organisationSignupDto));
        return ResponseEntity.noContent().build();
    }

    private <T> Resources<?> getResource(Iterable<T> active, Class<T> clazz) {
        Resources<?> resources;
        if (!active.iterator().hasNext()) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(clazz);
            List<Object> content = Collections.singletonList(wrapper);

            resources = new Resources<>(content);
        } else {
            resources = new Resources<>(active);
        }
        return resources;
    }
}
