package com.clone.service;

import com.clone.entity.AppUser;
import com.clone.exception.ResourceNotFoundException;
import com.clone.payload.AppUserDto;
import com.clone.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private AppUserRepository appUserRepository;
    private ModelMapper modelMapper;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }

    public AppUserDto createUser(AppUserDto dto) {
        AppUser user = mapToEntity(dto);
        AppUser savedUser = appUserRepository.save(user);
        AppUserDto appUserDto = mapToDto(savedUser);
        return appUserDto;
    }

    private AppUserDto mapToDto(AppUser savedUser) {
        AppUserDto dto = modelMapper.map(savedUser, AppUserDto.class);
        return dto;
    }

    private AppUser mapToEntity(AppUserDto dto) {
        AppUser user = modelMapper.map(dto, AppUser.class);
        return user;
    }

    public List<AppUserDto> getAllRegisteredUsers() {
        List<AppUser> appUsers = appUserRepository.findAll();
        List<AppUserDto> appUserDtos = appUsers.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return appUserDtos;
    }

    public void delete(Long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser updateUser(Long id, AppUserDto appUserDto) {
        AppUser appUser = appUserRepository.findById(id).get();
        appUser.setUsername(appUser.getUsername());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setName(appUserDto.getName());
        appUser.setPassword(appUser.getPassword());
        AppUser save = appUserRepository.save(appUser);
        return save;
    }

    public AppUserDto getUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record Not Found")
        );
        AppUserDto appUserDto = mapToDto(appUser);
        return appUserDto;
    }
}
