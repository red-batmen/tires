package ru.batmen.red.mvcapp.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;
import java.util.Date;

@Entity
@Table(name="banner_images")
public class BannerImage implements Serializable {

    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DISABLED = 0;

    public void setDescription(String description) {
        this.description = description;
    }

    public static final String BANNER_IMAGES_DIR = "/bannerImages/";

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Transient
    MultipartFile file;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @SequenceGenerator(name="banner_images_id_seq", sequenceName="banner_images_id_seq", allocationSize=1)
    private long Id;

    @Column(name="updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String title;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    private String imagepath;

    private String description;

    private int state;

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

    public String getDescription() {
        return description;
    }

    public void setDescriptionn(String description) {
        this.description = description;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void saveBannerImage(String staticPath) throws IOException
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
            File serverDir = new File(staticPath + BannerImage.BANNER_IMAGES_DIR);

            if (!serverDir.exists()){
                serverDir.mkdirs();
            }

            File serverFile = new File(staticPath + BannerImage.BANNER_IMAGES_DIR + File.separator + fileUniqueName);
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

    public String getStateText(){
        if (state == STATE_ACTIVE){
            return "активен";
        }else{
            return "отключен";
        }
    }
}
