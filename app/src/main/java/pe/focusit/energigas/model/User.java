package pe.focusit.energigas.model;

import org.apache.commons.text.WordUtils;

public class User {

    private Long id;
    private String username;
    private String documentNumber;
    private String name;
    private String lastName;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getName() {
        return name == null ? "" : WordUtils.capitalizeFully(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        String fullName = (name != null ? name.trim() : "") + " " + (lastName != null ? lastName.trim() : "");
        return WordUtils.capitalizeFully(fullName);
    }

}
