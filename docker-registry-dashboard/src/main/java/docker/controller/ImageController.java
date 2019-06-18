package docker.controller;


import docker.bean.ImageListResponse;
import docker.service.ShowDockerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    ShowDockerInfoService showDockerInfoService;

    @GetMapping("/images")
    public ImageListResponse getAllImages() {
        return showDockerInfoService.getAllImages();
    }

    @DeleteMapping("/images/{imageNmae}/{version}")
    public boolean deleteImages(@PathVariable String imageNmae,
                                @PathVariable String version) {
        String imgName = "";
        // image name 为拼接的3 个 _
        if (!imageNmae.contains("___")) {
            imgName = imageNmae;
        } else {
            String[] names = imageNmae.split("___");

            for (int i = 0; i < names.length; i++) {
                if (i == 0) {
                    imgName += names[i] + "/";
                } else {
                    imgName += names[i];
                }
            }
        }


        System.out.println(imgName);
        System.out.println(version);
        return showDockerInfoService.deleteImage(imgName, version);
    }

}
