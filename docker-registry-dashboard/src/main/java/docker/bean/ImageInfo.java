package docker.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {

    String imageName;
    String imageTage;

    String imageNamePre;
    String imageNameAfter;

    public String groupByPrefix() {
        if (imageName.contains("/")) {
            String[] splits = imageName.split("/");
            return splits[0];
        }
        return "";
    }

}
