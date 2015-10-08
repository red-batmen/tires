package ru.batmen.red.mvcapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.batmen.red.mvcapp.model.BannerImage;
import ru.batmen.red.mvcapp.model.Product;
import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.service.BannerImageService;
import ru.batmen.red.mvcapp.service.ProductService;
import ru.batmen.red.mvcapp.service.ManufactorerService;


@Controller
public class DefaultController {

    private ProductService productService;
    private ManufactorerService manufactorerService;
    private BannerImageService bannerImageService;
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("listTires", productService.getProducts(Manufactorer.TYPE_TIRE, 0, 4));
        model.addAttribute("listDrives", productService.getProducts(Manufactorer.TYPE_DRIVE, 0, 4));
        model.addAttribute("listBannerImage", bannerImageService.listBannerImage());
        model.addAttribute("bannerImagesDir", BannerImage.BANNER_IMAGES_DIR);
        return "frontend/index";
    }

}
