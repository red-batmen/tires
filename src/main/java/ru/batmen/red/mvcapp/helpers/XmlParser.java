package ru.batmen.red.mvcapp.helpers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.model.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {

    //xls файл
    private String filePath;

    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    //мапа произврдителей
    private Map<String, Manufactorer> mapManufactore = new HashMap<String, Manufactorer>();
    private Map<String, Product> mapProduct = new HashMap<String, Product>();

    public Map<String, Manufactorer> getMapManufactores() {
        return mapManufactore;
    }

    public Map<String, Product> getMapProducts() {
        return mapProduct;
    }

    //готовы ли данные
    private boolean isParsed = false;

    public XmlParser(String fileName){
        this.filePath = fileName;
    }

    public Map<String, Manufactorer> getMapManufactorers(){
        return this.mapManufactore;
    }

    public void parse() {
        //регудярки сезонов
        final Pattern patternManufactorerWinter = Pattern.compile("зима", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        final Pattern patternManufactorerSummer = Pattern.compile("лето", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        //регудярки типов
        final Pattern patternProductTypeDrive = Pattern.compile("([\\d\\*,\\.]+ +)?([\\d,\\*\\.]+ +)?([\\wА-Яа-я\\d\\.,]+ +)?([\\wА-Яа-я\\d,\\.]+ +)?(.+)(, шт)", Pattern.CASE_INSENSITIVE  | Pattern.UNICODE_CASE);
        //final Pattern patternProductTypeDrive = Pattern.compile("(([\\d,\\.\\*]+)? ([\\d,\\.\\*]+)? ([\\w\\d,\\.а-яА-Я]+)? ([\\w\\d,\\.а-яА-Я]+)?) ([\\wа-яА-Я() ]+)(, шт)?$", Pattern.CASE_INSENSITIVE);

        InputStream in = null;
        HSSFWorkbook wb = null;

        try {
            in = new FileInputStream(this.filePath);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //на вторйо странице
        Sheet sheet = wb.getSheetAt(1);
        //итератор строк
        Iterator<Row> it = sheet.iterator();

        //заголовок в 2х строках
        if (it.hasNext()) it.next();
        if (it.hasNext()) it.next();


        //цена товара
        Float price;
        //итератор ячеек
        Iterator<Cell> cells;
        //ячейка
        Cell cell;
        //тип производителя шины/диски
        int type;
        //сезон товара
        int season = 0;

        //номенклотура из ячейки
        String nomenclotureAttr = new String();
        //мануфактура из ячейки
        String manufacurerAttr = new String();
        //мануфактура готовая
        String manufactore;

        //для определения производителя
        int startManufactorerMatch;
        Boolean matcherFindManufactorer;
        //для определения номенклотуры
        String nomenclotureAtts;

        //продукт название
        String clearNomencloture;

        //аттрибуты шин
        String[] attsTire;

        //разделительный пробел в аттрибутах шин
        int separatorTire;

        //чистка
        this.isParsed = false;
        this.mapManufactore.clear();
        this.mapProduct.clear();

        int counter = 0;

        try {
            while (it.hasNext()) {
                Row row = it.next();
                cells = row.iterator();

                counter++;

                //парсим ячейки строки xml

                //номенклотура
                if (cells.hasNext()) {
                    cell = cells.next();
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        nomenclotureAttr = new String(cell.getStringCellValue());
                    }
                }

                //производитель
                if (cells.hasNext()) {
                    cell = cells.next();
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        manufacurerAttr = new String(cell.getStringCellValue());
                    }
                }

                //остатки
                if (cells.hasNext()) {
                    cell = cells.next();
                }

                //цена
                price = 0.0f;
                if (cells.hasNext()) {
                    cell = cells.next();
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        price = (float) cell.getNumericCellValue();
                    }
                }
                //конец парсим ячейки строки xml

                //парсим мануфактуру
                startManufactorerMatch = 0;
                matcherFindManufactorer = false;

                //производитель
                Matcher matcherWinter = patternManufactorerWinter.matcher(manufacurerAttr);

                //зима
                if (matcherWinter.find()) {
                    matcherFindManufactorer = true;
                    season = Product.SEASON_WINTER;
                    startManufactorerMatch = matcherWinter.start();
                } else {
                    //лето
                    Matcher matcherSummer = patternManufactorerSummer.matcher(manufacurerAttr);
                    if (matcherSummer.find()) {
                        matcherFindManufactorer = true;
                        season = Product.SEASON_SUMMER;
                        startManufactorerMatch = matcherSummer.start();
                    }
                }

                if (matcherFindManufactorer) {
                    manufactore = manufacurerAttr.substring(0, startManufactorerMatch-1);
                } else {
                    manufactore = manufacurerAttr.trim();
                }
                //конец парсим мануфактуру

                //парсим номенклотуру
                //определитм тип диск или шина
                separatorTire = nomenclotureAttr.indexOf(" ");
                nomenclotureAtts = new String(nomenclotureAttr.substring(0, separatorTire));

                //шины
                if (StringUtils.countOccurrencesOf(nomenclotureAtts, "/")>=1) {
                    attsTire = nomenclotureAtts.split("/");

                    clearNomencloture = new String(nomenclotureAttr.substring(separatorTire, nomenclotureAttr.length()));
                    clearNomencloture = clearNomencloture.replace(", шт", "").trim();

                    Product product = new Product();
                    product.setState(Product.STATE_ACTIVE);
                    product.setPrice(price);
                    product.setNomencloture(clearNomencloture);
                    product.setTireSeason(season);
                    product.setManufactorerName(manufactore);
                    try {
                        product.setTireWidth(Float.parseFloat(attsTire[0]));
                    }catch (NumberFormatException e){
                        continue;
                    }

                    if (attsTire.length >= 2) {
                        //String tmpStr = attsTire[2].replaceAll("/[A-Za-zА-Яа-я]+/", "");
                        String tmpStr = attsTire[1].replaceAll("\\D+", "");
                        product.setTireHeightProfile(Float.parseFloat(tmpStr));
                    }
                    if (attsTire.length >= 3) {
                        String tmpStr = attsTire[2].replaceAll("\\D+", "");
                        product.setTireDiameterRim(Float.parseFloat(tmpStr));
                    }

                    mapProduct.put(clearNomencloture, product);
                    logger.info(product.toString());

                    type = Manufactorer.TYPE_TIRE;
                    //определитсья с аттрибутами товара
                } else if (StringUtils.countOccurrencesOf(nomenclotureAtts, "*")>=1){
                    //диски
                    Product product = new Product();
                    product.setManufactorerName(manufactore);
                    product.setState(Product.STATE_ACTIVE);
                    product.setPrice(price);

                    List<String> allMatches = new ArrayList<String>();
                    Matcher m = patternProductTypeDrive.matcher(nomenclotureAttr);
                    while (m.find()) {
                        for(int i = 0; i<m.groupCount(); i++){
                            if (m.group(i) != null){
                                allMatches.add(((String)m.group(i)).trim());
                            }
                        }
                    }

                    int allMathesSize = allMatches.size();
                    //ничего не нашло
                    if (allMathesSize < 2) {
                        continue;
                    }
                    //номенклотура
                    product.setNomencloture(allMatches.get(allMathesSize-1));

                    if (allMathesSize > 2){
                        product.setDriveFirstPart(allMatches.get(1));
                        if (product.getDriveFirstPart().contains("втокамера")) {
                            continue;
                        }
                    }
                    if (allMathesSize > 3){
                        product.setDriveSecondPart(allMatches.get(2));
                    }
                    if (allMathesSize > 4){
                        product.setDriveOuter(allMatches.get(3));
                    }
                    if (allMathesSize > 5){
                        product.setDriveCenterPummetDiameter(allMatches.get(4));
                    }
                    type = Manufactorer.TYPE_DRIVE;
                    mapProduct.put(product.getNomencloture(), product);
                }else{
                    continue;
                }
                //конец парсим номенклотуру

                //добавим в мапу произвордителя
                if (!this.mapManufactore.containsKey(manufactore)) {
                    Manufactorer man = new Manufactorer();
                    man.setState(Manufactorer.STATE_ACTIVE);
                    man.setTitle(manufactore);
                    man.setType(type);
                    this.mapManufactore.put(manufactore, man);
                    logger.info(man.toString());
                }
            }
            this.isParsed = true;
        } catch (Exception e) {
            logger.error("Error parsing file", e);
        }

    }

}

