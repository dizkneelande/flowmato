package com.tests;

import com.example.flowmato.model.Achievements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ApplicationExtension.class)
public class AchievementsTest {
    private Achievements achievements;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.now();
        achievements = new Achievements(1, "Overgrown", testDate);
    }

    @Test
    void testConstructorAndGets() {
        assertEquals(1, achievements.getProfileId());
        assertEquals("Overgrown", achievements.getAchievementType());
        assertEquals(testDate, achievements.getAchievedOn());
    }

    @Test
    void testSetters() {
        achievements.setId(2);
        assertEquals(2, achievements.getId());
        achievements.setProfileId(3);
        assertEquals(3, achievements.getProfileId());
        achievements.setAchievementType("Procrastinator");
        assertEquals("Procrastinator", achievements.getAchievementType());
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        achievements.setAchievedOn(newDate);
        assertEquals(newDate, achievements.getAchievedOn());
    }
}
