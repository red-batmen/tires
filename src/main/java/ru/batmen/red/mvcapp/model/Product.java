package ru.batmen.red.mvcapp.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;
import java.security.Timestamp;
import java.util.Date;

@Entity
@Table(name="products")
public class Product implements Serializable {

    public static final int STATE_ACTIVE = 1;
    public static final int STATE_REMOVED = 2;

    public static final int SEASON_WINTER = 1;
    public static final int SEASON_SUMMER = 0;

    public static final String PRODUCT_IMAGES_DIR = "/products/";

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
    //@SequenceGenerator(name="manufactorers_id_seq", sequenceName="manufactorers_id_seq", allocationSize=1)
    private long id;

    private String nomencloture;

    @Column(name="tire_width")
    private Float tireWidth;
    @Column(name="tire_height_profile")
    private Float tireHeightProfile;
    @Column(name="tire_diameter_rim")
    private Float tireDiameterRim;

    @Column(name="drive_first_part")
    private String driveFirstPart;
    @Column(name="drive_second_part")
    private String driveSecondPart;
    @Column(name="drive_outer")
    private String driveOuter;
    @Column(name="drive_center_pummet_diameter")
    private String driveCenterPummetDiameter;

    @Column(name="updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Transient
    private String manufactorerName;

    private int state;

    @Column(name="tire_season")
    private int tireSeason;

    private Float price;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    private String imagepath;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne()
    @JoinColumn(name = "manufactorer_id", nullable = false)
    private Manufactorer manufactorer;

    public long getManufactorerId() {
        return manufactorerId;
    }

    public void setManufactorerId(long manufactorerId) {
        this.manufactorerId = manufactorerId;
    }

    @Transient
    private long manufactorerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomencloture() {
        return nomencloture;
    }

    public void setNomencloture(String nomencloture) {
        this.nomencloture = nomencloture;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTireSeason() {
        return tireSeason;
    }

    public void setTireSeason(int tireSeason) {
        this.tireSeason = tireSeason;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Manufactorer getManufactorer() {
        return manufactorer;
    }

    public void setManufactorer(Manufactorer manufactorer) {
        this.manufactorer = manufactorer;
    }

    public String getManufactorerName() {
        return manufactorerName;
    }

    public void setManufactorerName(String manufactorerName) {
        this.manufactorerName = manufactorerName;
    }

    public Float getTireWidth() {
        return tireWidth;
    }

    public void setTireWidth(Float tireWidth) {
        this.tireWidth = tireWidth;
    }

    public Float getTireHeightProfile() {
        return tireHeightProfile;
    }

    public void setTireHeightProfile(Float tireHeightProfile) {
        this.tireHeightProfile = tireHeightProfile;
    }

    public Float getTireDiameterRim() {
        return tireDiameterRim;
    }

    public void setTireDiameterRim(Float tireDiameterRim) {
        this.tireDiameterRim = tireDiameterRim;
    }

    public String getDriveFirstPart() {
        return driveFirstPart;
    }

    public void setDriveFirstPart(String driveFirstPart) {
        this.driveFirstPart = driveFirstPart;
    }

    public String getDriveSecondPart() {
        return driveSecondPart;
    }

    public void setDriveSecondPart(String driveSecondPart) {
        this.driveSecondPart = driveSecondPart;
    }

    public String getDriveOuter() {
        return driveOuter;
    }

    public void setDriveOuter(String driveOuter) {
        this.driveOuter = driveOuter;
    }

    public String getDriveCenterPummetDiameter() {
        return driveCenterPummetDiameter;
    }

    public void setDriveCenterPummetDiameter(String driveCenterPummetDiameter) {
        this.driveCenterPummetDiameter = driveCenterPummetDiameter;
    }

    public String getTireSeasonChecked(int season){
        if (this.tireSeason == season) return "checked";
        return "";
    }

    public String getTireSeasonAsString(){
        String result;
        if (this.tireSeason == Product.SEASON_WINTER){
            result = "зима";
        }else{
            result = "лето";
        }
        return result;
    }

    public void saveProductImage(String staticPath) throws IOException
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
            File serverDir = new File(staticPath + Product.PRODUCT_IMAGES_DIR);

            if (!serverDir.exists()){
                serverDir.mkdirs();
            }

            File serverFile = new File(staticPath + Product.PRODUCT_IMAGES_DIR + File.separator + fileUniqueName);
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
            return "удален";
        }
    }

    public String toString(){
        return "Product["+
                "|| manufactorer = "+manufactorer+
                "|| nomencloture = "+nomencloture+
                "|| tireSeason = "+ tireSeason +
                "|| price = "+price+
                "|| tireDiameterRim = "+tireDiameterRim+
                "|| tireHeightProfile = "+ tireHeightProfile +
                "|| tireWidth = "+tireWidth+
                "|| driveCenterPummetDiameter = "+driveCenterPummetDiameter+
                "|| driveFirstPart = "+driveFirstPart+
                "|| driveOuter = "+driveOuter+
                "|| driveSecondPart = "+driveSecondPart+
                "]";
    }
}
