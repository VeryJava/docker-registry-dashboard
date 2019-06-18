package docker.controller;

import docker.bean.ImageInfo;
import docker.service.ShowDockerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UiController {

    @Autowired
    ShowDockerInfoService showDockerInfoService;


    @GetMapping("/")
    public String index(Map<String, Object> model) {

        System.out.println("-----------get all images -----------");
        model.put("time", new Date());
        model.put("message", "good!");
        model.put("imagesSize", showDockerInfoService.getAllImages().getImages().size());
        model.put("result", showDockerInfoService.getAllImages());
        model.put("images", showDockerInfoService.getAllImages().getImages());

        List<ImageInfo> images = showDockerInfoService.getAllImages().getImages();
        Map<String, List<ImageInfo>>groupdsImages = groupImage(images);

        model.put("groupdsImages", groupdsImages);
        return "index";
    }



    public Map<String, List<ImageInfo>> groupImage(List<ImageInfo> allImages) {
        // group
        Map<String, List<ImageInfo>> grouped = allImages.stream()
                .collect(Collectors.groupingBy(ImageInfo::groupByPrefix));


//        List<List<ImageInfo>> imageList = new ArrayList<>();
//        for (String key : grouped.keySet()) {
//            System.out.println("key : " + key);
//            imageList.add(grouped.get(key));
//        }
//        System.out.println("grouped: " + imageList.size());
        return grouped;
    }

}
