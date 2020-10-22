package az.ibar.etaskify.service.organization;

import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.repository.OrganizationRepository;
import az.ibar.etaskify.service.user.UserPrincipalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;
    private UserPrincipalService userPrincipalService;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, UserPrincipalService userPrincipalService) {
        this.organizationRepository = organizationRepository;
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    public Organization create(Organization organization) {
        log.info("Creating organization: {}", organization);
        return organizationRepository.save(organization);
    }

    @Override
    public Organization getMyOrganization() {
        log.info("Getting my organization");
        User user = userPrincipalService.getUser();
        return user.getOrganization();
    }

}
