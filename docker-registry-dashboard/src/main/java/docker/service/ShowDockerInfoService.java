package docker.service;



import docker.bean.ImageListResponse;


public interface ShowDockerInfoService {

    ImageListResponse getAllImages();

    boolean deleteImage(String imageName, String version);

}
