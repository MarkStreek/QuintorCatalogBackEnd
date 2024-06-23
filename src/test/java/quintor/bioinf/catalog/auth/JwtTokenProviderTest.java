package quintor.bioinf.catalog.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import quintor.bioinf.catalog.services.JwtService;
import quintor.bioinf.catalog.services.UserService;

import java.util.Collection;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtTokenProviderTest {

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = mock(HttpServletRequest.class);
        userService = mock(UserService.class);
        when(userService.userDetailsService()).thenReturn(mock(UserDetailsService.class));
    }

    @Test
    public void testDoFilterInternal_noToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testPasswordEncoder() {
        PasswordConfig passwordConfig = new PasswordConfig();
        PasswordEncoder passwordEncoder = passwordConfig.passwordEncoder();
        assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/devices"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldNotGenerateAuthToken() throws Exception {
        UserDetails userDetails = mock(UserDetails.class);
        when(userService.userDetailsService().loadUserByUsername("john")).thenReturn(userDetails);
        String token = jwtService.generateToken(userService.userDetailsService().loadUserByUsername("john"));

        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("/devices")
                .header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldGenerateAuthToken() {

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "users";
            }

            @Override
            public String getUsername() {
                return "info@test.nl";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

}
