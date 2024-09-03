package com.jumkid.garage.service;

import com.jumkid.garage.config.TestObjectsProperties;
import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.repository.GarageProfileRepository;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.service.handler.DTOHandler;
import com.jumkid.garage.service.mapper.GarageProfileMapper;
import com.jumkid.garage.service.mapper.MapperContext;
import com.jumkid.share.user.UserProfile;
import com.jumkid.share.user.UserProfileManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GarageProfileServiceTests {

    private GarageProfile garageProfile;

    private GarageProfileEntity garageProfileEntity;

    @MockBean
    UserProfileManager userProfileManager;

    @Autowired
    DTOHandler dtoHandler;

    @Autowired
    private GarageProfileMapper garageProfileMapper;
    @Autowired
    private MapperContext mapperContext;

    @MockBean
    private GarageProfileRepository garageProfileRepository;

    private GarageMgmtService garageMgmtService;

    @Autowired
    private TestObjectsProperties testObjectsProperties;

    @BeforeAll
    void setup() {
        garageProfile = testObjectsProperties.getGarageProfile();
        garageProfileEntity = garageProfileMapper.dtoToEntity(garageProfile, new GarageProfileEntity(), mapperContext);

        garageMgmtService = new GarageMgmtServiceImpl(garageProfileRepository, garageProfileMapper, mapperContext, dtoHandler);
    }

    @Test
    void withId_shouldGetGarageProfile() throws GarageProfileNotFoundException {
        when(garageProfileRepository.findById(anyLong())).thenReturn(Optional.of(garageProfileEntity));
        GarageProfile garageProfile = garageMgmtService.getGarageProfile(1L);

        assertNotNull(garageProfile);
        assertEquals(garageProfileEntity.getDisplayName(), garageProfile.getDisplayName());
    }

    @Test
    void withInvalidId_shouldThrowNotFoundException() {
        long id = -1L;
        when(garageProfileRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(GarageProfileNotFoundException.class, () -> garageMgmtService.getGarageProfile(id));
    }

    @Test
    void shouldSaveGarageProfile() throws GarageProfileDuplicateDisplayNameException {

        garageProfileEntity.setId(1L);
        when(userProfileManager.fetchUserProfile())
                .thenReturn(UserProfile.builder().id(UUID.randomUUID().toString()).build());
        when(garageProfileRepository.save(any())).thenReturn(garageProfileEntity);

        GarageProfile newGarageProfile = garageMgmtService.addGarageProfile(garageProfile);

        assertNotNull(newGarageProfile);
        assertEquals(garageProfileEntity.getId(), newGarageProfile.getId());
    }

    @Test
    void shouldUpdateGarageProfile() throws GarageProfileNotFoundException, GarageProfileDuplicateDisplayNameException {
        long garageProfileId = 1L;
        garageProfileEntity.setId(garageProfileId);
        garageProfileEntity.setModifiedOn(garageProfile.getModifiedOn());
        garageProfileEntity.getGarageLocationEntityList()
                .get(0).setModifiedOn(garageProfile.getGarageLocations().get(0).getModifiedOn());

        when(userProfileManager.fetchUserProfile())
                .thenReturn(UserProfile.builder().id(UUID.randomUUID().toString()).build());
        when(garageProfileRepository.findById(garageProfileId)).thenReturn(Optional.of(garageProfileEntity));
        when(garageProfileRepository.save(any())).thenReturn(garageProfileEntity);

        GarageProfile updateGarageProfile = garageMgmtService.updateGarageProfile(garageProfileId, garageProfile);

        assertNotNull(updateGarageProfile);
        assertEquals(garageProfileEntity.getId(), updateGarageProfile.getId());
    }
}
