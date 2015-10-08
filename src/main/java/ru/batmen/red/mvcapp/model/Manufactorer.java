package ru.batmen.red.mvcapp.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="manufactorers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title"), @UniqueConstraint(columnNames = "id")})
public class Manufactorer implements Serializable {

    public static final int STATE_ACTIVE = 1;
    public static final int STATE_REMOVED = 2;

    public static final int TYPE_DRIVE = 0;
    public static final int TYPE_TIRE = 1;
    public static final int TYPE_DRIVE_AND_TIRE = 2;

    public static final String MANUFACTORER_IMAGES_DIR = "/manufactorers/";

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Transient
    MultipartFile file;
    public Manufactorer(){

    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    private String imagepath;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @SequenceGenerator(name="manufactorers_id_seq", sequenceName="manufactorers_id_seq", allocationSize=1)
    private long Id;

    @Column(name="updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private int type;

    private String title;

    private int state;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "manufactorer")
    @OneToMany()
    @JoinColumn(name = "id")
    private Set<Product> setProducts = new HashSet<Product>(0);

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Product> getSetProducts() {
        return this.setProducts;
    }

    public void setSetProducts(Set<Product> setProducts) {
        this.setProducts = setProducts;
    }

    public void saveManufactorerImage(String staticPath) throws IOException
    {
        MultipartFile file = getFile();
        String fileUniqueName = getImagepath();

        if (!file.isEmpty()) {
            if (fileUniqueName == null || fileUniqueName.equals("") ){
                fileUniqueName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(32),
                        FilenameUtils.getExtension(file.getOriginalFilename()));
            }

            byte[] bytes = file.getBytes();

            // Creating the directory to store file
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "tmpFiles");
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server
            File serverDir = new File(staticPath + Manufactorer.MANUFACTORER_IMAGES_DIR);

            if (!serverDir.exists()){
                serverDir.mkdirs();
            }

            File serverFile = new File(staticPath + Manufactorer.MANUFACTORER_IMAGES_DIR + File.separator + fileUniqueName);
            if (serverFile.exists()){
                serverFile.delete();
            }

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        }

        setImagepath(fileUniqueName);
    }

    public String getTypeCheked(int type){
        if (this.type == type) return "checked";
        return "";
    }

    public String getStateText(){
        if (state == STATE_ACTIVE){
            return "активен";
        }else{
            return "удален";
        }
    }

    public String getTypeText(){
        if (type == TYPE_DRIVE){
            return "диски";
        }else{
            return "шины";
        }
    }

}
