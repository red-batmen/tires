package ru.batmen.red.mvcapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.batmen.red.mvcapp.helpers.XmlParser;
import ru.batmen.red.mvcapp.model.BannerImage;
import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.model.Product;
import ru.batmen.red.mvcapp.service.BannerImageService;
import ru.batmen.red.mvcapp.service.ManufactorerService;
import ru.batmen.red.mvcapp.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class AdminController {

    private ProductService productService;
    private ManufactorerService manufactorerService;
    private BannerImageService bannerImageService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private String projectPath;

    public AdminController(){
        projectPath = "/opt/projects/java/webapp/target/webapp/WEB-INF/resources/static";
    }

    @Autowired(required=true)
    @Qualifier(value="ManufactorerService")
    public void setManufactorerService(ManufactorerService manufactorerService) {
        this.manufactorerService = manufactorerService;
    }

    @Autowired(required=true)
    @Qualifier(value="BannerImageService")
    public void setBannerImageService(BannerImageService bannerImageService) {
        this.bannerImageService = bannerImageService;
    }

    @Autowired(required=true)
    @Qualifier(value="ProductService")
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * работа с производителями начало
     * @param model
     * @return
     */
    @RequestMapping(value = "/createManufactorer", method = RequestMethod.GET)
    public String createManufactorerForm(Model model){
        model.addAttribute("manufactorer", new Manufactorer());
        return "backend/manufactorer/create";
    }

    @ResponseBody
    @RequestMapping(value = "/createManufactorer", method = RequestMethod.POST)
    public String createManufactorer(
        @ModelAttribute("manufactorer") Manufactorer manufactorer, HttpServletRequest request)
    {
        try {
            //manufactorer.saveManufactorerImage(request.getSession().getServletContext().getRealPath("/") + "resources/static/");
            manufactorer.saveManufactorerImage(projectPath);
        }catch (IOException e){
            logger.error("error saving manufactorer image", e);
        }
        this.manufactorerService.addManufactorer(manufactorer);
        return "redirect:/listManufactorers";
    }

    @RequestMapping(value = "/editManufactorer/{id}")
    public String editManufactorerForm(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("manufactorerDir", Manufactorer.MANUFACTORER_IMAGES_DIR);
        model.addAttribute("manufactorer", this.manufactorerService.getManufactorer(id));
        return "backend/manufactorer/edit";
    }

    @RequestMapping(value = "/editManufactorer/{id}", method = RequestMethod.POST)
    public String editBannerImage(@PathVariable("id") long id,
        @ModelAttribute("manufactorer") Manufactorer manufactorer, Model model, HttpServletRequest request)
    {
        try {
            //manufactorer.saveManufactorerImage(request.getSession().getServletContext().getRealPath("/") + "resources/static/");
            manufactorer.saveManufactorerImage(projectPath);
        }catch (IOException e){
            logger.error("error saving banner image", e);
        }

        if (manufactorer.getId()==0){
            manufactorerService.addManufactorer(manufactorer);
        }else{
            manufactorer.setId(id);
            this.manufactorerService.updateManufactorer(manufactorer);
        }
        return "redirect:/listManufactorer";
    }

    @RequestMapping("/removeManufactorer/{id}")
    public String removeManufactorer(@PathVariable("id") int id){
        manufactorerService.removeManufactorer(id);
        return "redirect:/listManufactorer";
    }

    @RequestMapping(value = "/listManufactorer")
    public String listManufactorer(Model model)
    {
        List<Manufactorer> listManufactorer = manufactorerService.listManufactorers();
        model.addAttribute("listManufactorer", listManufactorer);
        model.addAttribute("manufactorerDir", Manufactorer.MANUFACTORER_IMAGES_DIR);
        return "backend/manufactorer/list";
    }

    /**
     * работа с производителями конец
     */

    /**
     * работа с баннерами начало
     * @param model
     * @return
     */
    @RequestMapping(value = "/createProduct", method = RequestMethod.GET)
    public String createProductForm(Model model){
        model.addAttribute("listManufactorer", manufactorerService.listManufactorers());
        model.addAttribute("product", new Product());
        return "backend/product/create";
    }

    @ResponseBody
    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public String createProduct(
            @ModelAttribute("product") Product product, HttpServletRequest request)
    {
        try {
            //product.saveProductImage(request.getSession().getServletContext().getRealPath("/") + "resources/static/");
            Manufactorer man = manufactorerService.getManufactorer(product.getManufactorerId());
            product.setManufactorer(man);
            product.saveProductImage(projectPath);
        }catch (IOException e){
            logger.error("error saving banner image", e);
        }
        productService.addProduct(product);
        return "redirect:/listProduct";
    }

    @RequestMapping(value = "/editProduct/{id}")
    public String editProductForm(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("listManufactorer", manufactorerService.listManufactorers());
        model.addAttribute("productDir", Product.PRODUCT_IMAGES_DIR);
        model.addAttribute("product", productService.getProduct(id));
        return "backend/product/edit";
    }

    @RequestMapping(value = "/editProduct/{id}", method = RequestMethod.POST)
    public String editProduct(@PathVariable("id") long id,
                                  @ModelAttribute("product") Product product, Model model, HttpServletRequest request)
    {
        try {
            //product.saveProductImage(request.getSession().getServletContext().getRealPath("/") + "resources/static/");
            Manufactorer man = manufactorerService.getManufactorer(product.getManufactorerId());
            product.setManufactorer(man);
            product.saveProductImage(projectPath);
        }catch (IOException e){
            logger.error("error saving зroduct image", e);
        }

        if (product.getId()==0){
            productService.addProduct(product);
        }else{
            product.setId(id);
            productService.updateProduct(product);
        }
        return "redirect:/listProduct";
    }

    @RequestMapping("/removeProduct/{id}")
    public String removeProduct(@PathVariable("id") int id){
        productService.removeProduct(id);
        return "redirect:/listProduct";
    }

    @RequestMapping(value = "/listProduct")
    public String listProduct(Model model)
    {
        List<Product> listProduct= productService.listProducts();
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("productDir", Product.PRODUCT_IMAGES_DIR);
        return "backend/product/list";
    }
    /**
     * работа с баннерами конец
     */

    /**
     * работа с продуктами начало
     * @param model
     * @return
     */
    @RequestMapping(value = "/createBannerImage", method = RequestMethod.GET)
    public String createBannerImageForm(Model model){
        model.addAttribute("bannerImage", new BannerImage());
        return "backend/bannerImage/create";
    }

    @ResponseBody
    @RequestMapping(value = "/createBannerImage", method = RequestMethod.POST)
    public String createBannerImage(
            @ModelAttribute("bannerImage") BannerImage bannerImage, HttpServletRequest request)
    {
        try {
            //bannerImage.saveBannerImage(request.getSession().getServletContext().getRealPath("/") + "resources/static/");
            bannerImage.saveBannerImage(projectPath);
        }catch (IOException e){
            logger.error("error saving banner image", e);
        }
        this.bannerImageService.addBannerImage(bannerImage);
        return "redirect:/listBannerImage";
    }

    @RequestMapping(value = "/editBannerImage/{id}")
    public String editBannerImageForm(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("bannerImagesDir", BannerImage.BANNER_IMAGES_DIR);
        model.addAttribute("bannerImage", this.bannerImageService.getBannerImage(id));
        return "backend/bannerImage/edit";
    }

    @RequestMapping(value = "/editBannerImage/{id}", method = RequestMethod.POST)
    public String editBannerImage(@PathVariable("id") long id,
                                  @ModelAttribute("bannerImage") BannerImage bannerImage, Model model, HttpServletRequest request)
    {
        try {
            //bannerImage.saveBannerImage(request.getSession().getServletContext().getRealPath("/") + "resources/static/");
            bannerImage.saveBannerImage(projectPath);
        }catch (IOException e){
            logger.error("error saving banner image", e);
        }

        if (bannerImage.getId()==0){
            this.bannerImageService.addBannerImage(bannerImage);
        }else{
            bannerImage.setId(id);
            this.bannerImageService.updateBannerImage(bannerImage);
        }
        return "redirect:/listBannerImage";
    }

    @RequestMapping("/removeBannerImage/{id}")
    public String removeBannerImage(@PathVariable("id") int id){
        this.bannerImageService.removeBannerImage(id);
        return "redirect:/listBannerImage";
    }

    @RequestMapping(value = "/listBannerImage")
    public String listBannerImage(Model model)
    {
        List<BannerImage> listBannerImage = this.bannerImageService.listBannerImage();
        model.addAttribute("listBannerImage", listBannerImage);
        model.addAttribute("bannerImagesDir", BannerImage.BANNER_IMAGES_DIR);
        return "backend/bannerImage/list";
    }
    /**
     * работа с продуктами конец
     */


    //главная админки
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model){
        return "backend/admin";
    }

    //главная админки
    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public String priceForm(Model model){
        return "backend/price/index";
    }

    /**
     * Загрузка прайса
     * @param file
     * @param model
     * @return
     */
    @RequestMapping(value = "/parsePrice", method = RequestMethod.POST)
    public String parseXls(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) {

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                File priceDir = new File(projectPath + "/price");
                if (!priceDir.exists())
                    priceDir.mkdirs();

                // Create the file on server
                String filePath = projectPath + "/price/price.xls";
                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                XmlParser xmlParser = new XmlParser(filePath);
                xmlParser.parse();

                Map<String, Manufactorer> mapManufactores = xmlParser.getMapManufactores();
                Map<String, Product> mapProducts = xmlParser.getMapProducts();

                manufactorerService.renewalManufactorers(mapManufactores);
                productService.renewalProducts(mapProducts, mapManufactores);

            } catch (Exception e) {
                logger.error("Error update price list", e);
            }
        }

        return "redirect:/admin";
    }

    //Spring Security see this :
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("backend/admin/login");

        return model;
    }

}
