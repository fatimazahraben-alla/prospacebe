package ma.digital.prospace.service.dto;

public class CompteProContactDTO {
    private CompteProDTO comptePro;
    private ContactDTO contact;

    public CompteProContactDTO(CompteProDTO comptePro, ContactDTO contact) {
        this.comptePro = comptePro;
        this.contact = contact;
    }

    // Getters and Setters
    public CompteProDTO getComptePro() {
        return comptePro;
    }

    public void setComptePro(CompteProDTO comptePro) {
        this.comptePro = comptePro;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }
}

