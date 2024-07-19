package com.tawasalna.auth.businesslogic;
import com.tawasalna.auth.businesslogic.pmsassistance.AssistanceService;
import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.models.Assistance;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import com.tawasalna.auth.payload.request.AssistanceDTO;
import com.tawasalna.auth.repository.AssistanceRepository;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.mail.IMailingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AssistanceServiceTest {
    @Mock
    private AssistanceRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private IAuthUtilsService authUtilsService;
    @Mock
    private IMailingService mailingService;

    @InjectMocks
    private AssistanceService assistanceService;
    @Test
    void testAssignAssistance() {
        // Given
        AssistanceDTO assistanceDTO = new AssistanceDTO();
        assistanceDTO.setId("1213"); // Example ID

        Users agent = new Users(); // Mocking agent user
        agent.setId("35354"); // Example Agent ID
        Users broker = new Users(); // Mocking broker user
        broker.setId("212"); // Example Broker ID
        Users prospect = new Users(); // Mocking agent user
        prospect.setId("25435"); // Example Agent ID

        assistanceDTO.setAgentID(agent); // Setting agent user object
        assistanceDTO.setBrokerID(broker); // Setting broker user object
        assistanceDTO.setProspectID(prospect); // Example Prospect ID
        assistanceDTO.setDesiredDate(new Date());
        assistanceDTO.setDescription("Test description");

        // Stubbing repository methods
        when(repository.save(any(Assistance.class))).thenAnswer(invocation -> {
            Assistance assistance = invocation.getArgument(0);
            assistance.setId("1"); // Mocking saved ID
            return assistance;
        });

        // When
        Assistance assignedAssistance = assistanceService.assignAssistance(assistanceDTO);

        // Then
        verify(repository, times(1)).save(any(Assistance.class));
        assertEquals("1", assignedAssistance.getId()); // Assuming you are expecting ID 1
        assertEquals(AssistanceStatus.WAITING, assignedAssistance.getStatus());
        assertEquals(assistanceDTO.getDesiredDate(), assignedAssistance.getDesiredDate());
        assertEquals(assistanceDTO.getDescription(), assignedAssistance.getDescription());
    }

    @Test
    void testDisplayAssistance() {
        // Given
        List<Assistance> mockAssistances = new ArrayList<>();
        when(repository.findAll(Sort.by("createdAt").descending())).thenReturn(mockAssistances);
        // When
        List<Assistance> displayedAssistances = assistanceService.displayAssistance();
        // Then
        assertEquals(mockAssistances.size(), displayedAssistances.size());
    }

    @Test
    void testGetAssistancesWaintingByAgent() {
        // Given
        String agentId = "123"; // Example agent ID

        List<Assistance> mockAssistances = new ArrayList<>();

        // Stubbing repository method
        when(repository.findByAgentID_IdOrderByCreatedAtDesc(agentId)).thenReturn(mockAssistances);

        // When
        List<Assistance> waitingAssistances = assistanceService.getAssistancesWaintingByAgent(agentId);

        // Then
        List<Assistance> expectedWaitingAssistances = mockAssistances.stream()
                .filter(assistance -> assistance.getStatus() == AssistanceStatus.WAITING)
                .toList();

        assertEquals(expectedWaitingAssistances.size(), waitingAssistances.size());
    }

    @Test
    void testGetAssistancesWaintingByBroker() {
        // Given
        String brokerId = "123"; // Example agent ID

        List<Assistance> mockAssistances = new ArrayList<>();

        // Stubbing repository method
        when(repository.findByBrokerID_IdOrderByCreatedAtDesc(brokerId)).thenReturn(mockAssistances);

        // When
        List<Assistance> waitingAssistances = assistanceService.getAssistancesWaintingByBroker(brokerId);

        // Then
        List<Assistance> expectedWaitingAssistances = mockAssistances.stream()
                .filter(assistance -> assistance.getStatus() == AssistanceStatus.WAITING)
                .toList();

        assertEquals(expectedWaitingAssistances.size(), waitingAssistances.size());
    }

    @Test
    void testGetAssistancesAcceptedByAgent() {
        // Given
        String agentId = "123"; // Example agent ID

        List<Assistance> mockAssistances = new ArrayList<>();
        // Populate mockAssistances with some mock data
        // You can add some assistances with status ACCEPTED and some with other statuses for testing

        // Stubbing repository method
        when(repository.findByAgentID_IdOrderByCreatedAtDesc(agentId)).thenReturn(mockAssistances);

        // When
        List<Assistance> acceptedAssistances = assistanceService.getAssistancesAcceptedByAgent(agentId);

        // Then
        List<Assistance> expectedAcceptedAssistances = mockAssistances.stream()
                .filter(assistance -> assistance.getStatus() == AssistanceStatus.ACCEPTED)
                .toList();

        assertEquals(expectedAcceptedAssistances.size(), acceptedAssistances.size());
        // Add more assertions as needed to verify the correctness of the filtered list
    }

    @Test
    void testGetAssistancesByAgent() {
        // Given
        String agentId = "123"; // Example agent ID

        List<Assistance> mockAssistances = new ArrayList<>();
        // Populate mockAssistances with some mock data

        // Stubbing repository method
        when(repository.findByAgentID_IdOrderByCreatedAtDesc(agentId)).thenReturn(mockAssistances);

        // When
        List<Assistance> assistances = assistanceService.getAssistancesByAgent(agentId);

        // Then
        assertEquals(mockAssistances.size(), assistances.size());
        // Add more assertions as needed to verify the correctness of the returned list
    }

    @Test
    void testGetAssistancesByBrokers() {
        // Given
        String brokerId = "123"; // Example broker ID
        List<Assistance> mockAssistances = new ArrayList<>();
        // Populate mockAssistances with some mock data
        // Stubbing repository method
        when(repository.findByBrokerID_IdOrderByCreatedAtDesc(brokerId)).thenReturn(mockAssistances);
        // When
        List<Assistance> assistances = assistanceService.getAssistancesByBrokers(brokerId);
        // Then
        assertEquals(mockAssistances.size(), assistances.size());
    }
    @Test
    void testGetAssistancesByAgentwithPagination() {
        // Given
        String agentId = "123"; // Example agent ID
        int offset = 0;
        int pageSize = 10;

        List<Assistance> mockAssistances = new ArrayList<>();
        // Populate mockAssistances with some mock data

        // Create a mock Page object using PageImpl
        Pageable pageable = PageRequest.of(offset, pageSize);
        Page<Assistance> mockPage = new PageImpl<>(mockAssistances, pageable, mockAssistances.size());

        // Stubbing repository method
        when(repository.findByAgentID_IdOrderByCreatedAtDesc(agentId, pageable)).thenReturn(mockPage);

        // When
        Page<Assistance> assistancesPage = assistanceService.getAssistancesByAgentwithPagination(agentId, offset, pageSize);

        // Then
        assertEquals(mockPage.getTotalElements(), assistancesPage.getTotalElements());
        assertEquals(mockPage.getContent().size(), assistancesPage.getContent().size());
        // Add more assertions as needed to verify the correctness of the returned Page object
    }

    /*@Test
    void testAcceptAssistance() throws MessagingException {
        // Given
        AssistanceDTO assistanceDTO = new AssistanceDTO();
        assistanceDTO.setId("1L"); // Example assistance ID
        assistanceDTO.setDesiredDate("2024-04-10"); // Example desired date

        Assistance assistance = new Assistance();
        assistance.setId("1L");
        assistance.setStatus(AssistanceStatus.WAITING);
        assistance.setProspectID(new Users());

        Optional<Assistance> optionalAssistance = Optional.of(assistance);

        when(repository.findById(assistanceDTO.getId())).thenReturn(optionalAssistance);
        when(authUtilsService.encodePwd(anyString())).thenReturn("encodedPassword");

        // When
        ResponseEntity<Assistance> response = assistanceService.AcceptAssistance(assistanceDTO);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(AssistanceStatus.ACCEPTED, assistance.getStatus());
        assertEquals("2024-04-10", assistance.getDesiredDate());
        verify(repository, times(1)).save(assistance);
        verify(userRepository, times(1)).save(assistance.getProspectID());
        verify(mailingService, times(1)).sendEmail(anyString(), anyString(), anyString(), anyList());
    }*/
    @Test
    void testRejectAssistance() throws MessagingException {
        // Given
        AssistanceDTO assistanceDTO = new AssistanceDTO();
        assistanceDTO.setId("1L"); // Exemple d'ID d'assistance
        assistanceDTO.setDescription("This is a rejection reason");

        Assistance assistance = new Assistance();
        assistance.setId("1L");
        assistance.setStatus(AssistanceStatus.WAITING);
        Users prospect = new Users();
        prospect.setEmail("prospect@example.com");
        assistance.setProspectID(prospect);

        Optional<Assistance> optionalAssistance = Optional.of(assistance);

        when(repository.findById(assistanceDTO.getId())).thenReturn(optionalAssistance);

        // When
        ResponseEntity<Assistance> response = assistanceService.RejectAssistance(assistanceDTO);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(AssistanceStatus.REJECTED, assistance.getStatus());
        assertEquals("This is a rejection reason", assistance.getDescription());
        verify(repository, times(1)).save(assistance);
        verify(userRepository, times(1)).save(assistance.getProspectID());
        verify(mailingService, times(1)).sendEmail(eq("prospect@example.com"), any(), any(), anyList());
    }
    @Test
    void testArchiver() {
        // Given
        String assistanceId = "1L"; // exemple d'ID d'assistance
        Assistance assistance = new Assistance();
        assistance.setId(assistanceId);
        assistance.setStatus(AssistanceStatus.WAITING);

        when(repository.findById(assistanceId)).thenReturn(Optional.of(assistance));

        // When
        assistanceService.archiver(assistanceId);

        // Then
        verify(repository, times(1)).findById(assistanceId);
        verify(repository, times(1)).save(any(Assistance.class));
    }
}
