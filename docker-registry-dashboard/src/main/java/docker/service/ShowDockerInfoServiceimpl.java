package docker.service;

import docker.bean.ImageAll;
import docker.bean.ImageInfo;
import docker.bean.ImageListResponse;
import docker.bean.ImageTages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowDockerInfoServiceimpl implements ShowDockerInfoService {


    @Autowired
    RestTemplate restTemplate;

    final String REGISTRY_URI = "http://ip:5000";

    @Override
    public ImageListResponse getAllImages() {
        ImageListResponse imageListResponse = new ImageListResponse();

        List<ImageInfo> allImagesInfo = new ArrayList<>();

        // get all images
        ImageAll result = restTemplate.getForObject(
                REGISTRY_URI+"/v2/_catalog?n=10000",
                ImageAll.class);

        if (result == null || result.getRepositories().size() == 0) {
            imageListResponse.setStatus(false);
            imageListResponse.setMessage("No Message!");
            imageListResponse.setImages(null);
            return imageListResponse;
        }

        for (int i = 0; i < result.getRepositories().size(); i++) {
            ImageTages imageTages = restTemplate.getForObject(
                    REGISTRY_URI+"/v2/" + result.getRepositories().get(i) + "/tags/list",
                    ImageTages.class);

            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImageName(imageTages.getName());
//            String[] temp = imageTages.getName().split("/");
//            imageInfo.setImageNamePre(temp[0]);
//            imageInfo.setImageNameAfter(temp[1]);

            imageInfo.setImageTage(imageTages.getTags().get(0));
            allImagesInfo.add(imageInfo);
        }

        imageListResponse.setStatus(true);
        imageListResponse.setMessage("All images");
        imageListResponse.setSize(allImagesInfo.size());
        imageListResponse.setImages(allImagesInfo);
        System.out.println("------- get all images over --------");
        return imageListResponse;
    }

    @Override
    public boolean deleteImage(String imageName, String version) {
        System.out.println(imageName + "  name");

        String[] cmds1 = {"/bin/bash", "-c",
                String.format("docker exec registry  rm -rf /var/lib/registry/docker/registry/v2/repositories/%s", imageName)};

        System.out.println(String.format("docker exec registry  rm -rf /var/lib/registry/docker/registry/v2/repositories/%s", imageName).toString());

        ProcessBuilder pb = new ProcessBuilder(cmds1);
        pb.redirectErrorStream(true);

        String[] cmds2 = {"/bin/bash", "-c",
                String.format("docker exec -it registry  /bin/registry garbage-collect  /etc/docker/registry/config.yml")};
        ProcessBuilder pb2 = new ProcessBuilder(cmds2);
        pb2.redirectErrorStream(true);


        String[] cmds3 = {"/bin/bash", "-c",
                String.format("docker restart f0d49b9159ee")};
        ProcessBuilder pb3 = new ProcessBuilder(cmds3);
        pb3.redirectErrorStream(true);

        String[] cmds4 = {"/bin/bash", "-c",
                String.format("echo  1+1")};
        ProcessBuilder pb4 = new ProcessBuilder(cmds4);
        pb4.redirectErrorStream(true);
        Process p;
        Process p2;
        Process p3;
        Process p4;
        StringBuilder s = new StringBuilder();
        int c;
        InputStream inputStream;
        try {
            System.out.println("Step 1");
            p = pb.start();
            p.waitFor();

            inputStream = p.getInputStream();
            while ((c = inputStream.read()) != -1) {
                s.append((char) c);
            }
            System.out.println(s);


            System.out.println("Step 2");
            p2 = pb2.start();
            p2.waitFor();
//            System.out.println("Step 3");
//            p3 = pb3.start();
//            p3.waitFor();

            inputStream = p2.getInputStream();

            while ((c = inputStream.read()) != -1) {
                s.append((char) c);
            }
            System.out.println(s);


            System.out.println("Step 4");
            p4 = pb4.start();
            p4.waitFor();

            inputStream = p4.getInputStream();

            while ((c = inputStream.read()) != -1) {
                s.append((char) c);
            }
            System.out.println(s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
