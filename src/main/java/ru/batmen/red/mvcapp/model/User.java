package ru.batmen.red.mvcapp.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;
import java.util.Date;

@Entity
@Table(name="users")
public class User implements Serializable {

    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DISABLED = 0;

    public static final String ROLE_ADMIN_TEXT = "ROLE_ADMIN";
    public static final String ROLE_USER_TEXT = "ROLE_USER";

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 0;

    public String getRoleText(){
        if (role == ROLE_ADMIN){
            return ROLE_ADMIN_TEXT;
        }else{
            return ROLE_USER_TEXT;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private Integer state;
    private String address;
    private String name;
    private Integer role;

    @Column(name="updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public String getStateText(){
        if (state == STATE_ACTIVE){
            return "активен";
        }else{
            return "не активен";
        }
    }

    public String toString(){
        return "Product["+
                "|| email = "+email+
                "|| name = "+name+
                "|| role = "+ role +
                "|| state = "+state+
                "|| address = "+address+
                "]";
    }
}
