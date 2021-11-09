package com.example.mycompany.avitoparseapp.data.parser;

import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Document doc;
    private List<CarCell> carCells;

    public List<CarCell> getCarCells(String params) {
        if(carCells != null) carCells.clear();
        try {
            doc = Jsoup.connect("https://www.avito.ru/moskva_i_mo/avtomobili/" + params.toLowerCase() + "?cd=1&s=104").get();
            Elements elements = doc.select("[data-marker=item]");
            int listSize = doc.select("[data-marker=item]").size();
            carCells = new ArrayList<>();
            for (int i = 0; i < listSize; i++) {
                String previewImageHref;
                String firstImageHref;
                String itemName;
                String link;
                if (elements.get(i).children().size() > 1) {
                    Element previewImagePlaceHolder = elements.get(i).child(1).child(0).child(0).child(0).child(0).child(0).child(0);
                    if(!previewImagePlaceHolder.html().equals("")) {
                        previewImageHref = previewImagePlaceHolder.attr("data-marker").substring(19);
                        itemName = elements.get(i).child(1).child(1).child(1).child(0).child(0).html();
                        link = "https://www.avito.ru"
                                + elements.get(i).child(1).child(0).child(0).attr("href");
                    } else {
                        continue;
                    }
                } else {
                    previewImageHref = elements.get(i).child(0).child(0).child(0).child(0).child(0).child(0).child(0).attr("data-marker").substring(19);
                    itemName = elements.get(i).child(0).child(1).child(1).child(0).child(0).html();
                    link = "https://www.avito.ru"
                            + elements.get(i).child(0).child(0).child(0).attr("href");
                }
                carCells.add(new CarCell(previewImageHref, "", link, itemName));
            }
        } catch (IOException exception) {
            exception.getMessage();
            carCells = null;
        }
        return carCells;
    }

    public Car getCarInfo(CarCell carCell) {
        List<String> photosLinks = new ArrayList<>();
        String mainPhotoLink = null;
        String telephonePhotoLink = null;
        String carDescription = null;
        try {
            Document carPage = Jsoup.connect(carCell.getLinkToItem()).get();
            mainPhotoLink = carPage
                    .select("[class=gallery-imgs-container js-gallery-imgs-container]")
                    .get(0).child(0).child(0).attr("data-url");
            Elements photoWrappers
                    = carPage.select("[class=gallery-imgs-container js-gallery-imgs-container]").get(0).children();
            for(Element e : photoWrappers) {
                photosLinks.add(e.child(0).attr("data-url"));
            }
            if(carPage.select("[class=item-description-html]").size() == 0) {
                carDescription = carPage.select("[class=item-description-text]").get(0).html();
            } else {
                carDescription = carPage.select("[class=item-description-html]").get(0).html();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new Car(carCell.getCarName(), mainPhotoLink, telephonePhotoLink, photosLinks, carDescription);
    }
}
