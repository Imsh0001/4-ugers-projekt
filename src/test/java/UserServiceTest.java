
import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.User;
import com.example.sso.repository.UserRepository;
import com.example.sso.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new User("testuser", "test@example.com", "password123", "ROLE_ADMIN", null, null, null, null, null);
    }

    @Test
    public void testCreateAdmin_whenValidData_createsUser() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        User createdUser = userService.createAdmin("testuser", "test@example.com", "password123");

        // Assert
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    public void testCreateAdmin_whenUsernameAlreadyExists_throwsException() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.createAdmin("testuser", "new@example.com", "password123"));
        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    public void testCreateMember_whenValidData_createsUser() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        User createdUser = userService.createMember("testuser", "test@example.com", "password123",
                MembershipType.ACTIVE, Department.RECRUITMENT,
                "BSc Computer Science", null, "Bachelor");

        // Assert
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
    }

    @Test
    public void testCreateMember_whenActiveMemberWithoutDepartment_throwsException() {
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.createMember("testuser", "test@example.com", "password123",
                        MembershipType.ACTIVE, null, "BSc Computer Science",
                        null, "Bachelor"));
        assertEquals("Active members must select a department.", exception.getMessage());
    }

    @Test
    public void testCreateMember_whenPassiveMemberWithDepartment_throwsException() {
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.createMember("testuser", "test@example.com", "password123",
                        MembershipType.PASSIVE, Department.RECRUITMENT,
                        "BSc Computer Science", null, "Bachelor"));
        assertEquals("Passive members cannot select a department.", exception.getMessage());
    }

    @Test
    public void testAuthenticate_whenValidUsernameAndPassword_returnsUser() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // Act
        User authenticatedUser = userService.authenticate("testuser", "password123");

        // Assert
        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
    }

    @Test
    public void testAuthenticate_whenInvalidPassword_throwsException() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.authenticate("testuser", "wrongpassword"));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    public void testAuthenticate_whenUserNotFound_throwsException() {
        // Arrange
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.authenticate("nonexistentuser", "password123"));
        assertEquals("User not found", exception.getMessage());
    }
}
