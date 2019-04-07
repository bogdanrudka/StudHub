package org.hackathon.dto;

import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;
import java.util.List;

@Relation(value = "event", collectionRelation = "events")
public class EventDto {
    private long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private String location;
    private OrganisationDto owner;
    private List<VolunteerDto> participants;
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OrganisationDto getOwner() {
        return owner;
    }

    public void setOwner(OrganisationDto owner) {
        this.owner = owner;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<VolunteerDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<VolunteerDto> participants) {
        this.participants = participants;
    }
}
