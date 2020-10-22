package az.ibar.etaskify.service.organization;

import az.ibar.etaskify.model.Organization;

public interface OrganizationService {
    Organization create(Organization organization);
    Organization getMyOrganization();
}
