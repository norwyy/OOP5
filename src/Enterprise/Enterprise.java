package Enterprise;

import Enterprise.OrganizationGroup.Organization;

import java.util.ArrayList;
import java.util.List;

public class Enterprise {

    private List<Organization> organizations = new ArrayList<>();

    public void addOrganization(Organization org) {
        organizations.add(org);
    }


    public boolean removeOrganizationByName(String name) {
        return organizations.removeIf(org -> org.getName().contentEquals(name));
    }

    public Organization findOrganizationByName(String name) {
        for (Organization org : organizations)
            if (org.getName().contentEquals(name))
                return org;
        return null;
    }

    public void displayAllOrganizations() {
        for (Organization org : organizations)
            org.displayInfo();
    }

    public List<Organization> findOrganizationsByName(String name) {
        List<Organization> result = new ArrayList<>();
        for (Organization org : organizations)
            if (org.getName().contentEquals(name))
                result.add(org);
        return result;
    }

    public List<Organization> findOrganizationsByLocation(String location) {
        List<Organization> result = new ArrayList<>();
        for (Organization org : organizations)
            if (org.getLocation().contentEquals(location))
                result.add(org);
        return result;
    }

    public List<Organization> findOrganizationsByType(String type) {
        List<Organization> result = new ArrayList<>();
        for (Organization org : organizations)
            if (org.getClass().getSimpleName().contentEquals(type))
                result.add(org);
        return result;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }
}
