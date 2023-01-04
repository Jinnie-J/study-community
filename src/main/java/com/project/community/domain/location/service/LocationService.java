package com.project.community.domain.location.service;

import com.project.community.domain.location.entity.Location;
import com.project.community.domain.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @PostConstruct
    public void initLocation() throws IOException {
        if(locationRepository.count() == 0){
            Resource resource= new ClassPathResource("location.csv");
            List<Location> zoneList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .map(line ->{
                        String[] split = line.split(",");
                        return Location.builder().city(split[0]).localNameOfCity(split[1]).province(split[2]).build();
                    }).collect(Collectors.toList());
            locationRepository.saveAll(zoneList);
        }
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }
}
