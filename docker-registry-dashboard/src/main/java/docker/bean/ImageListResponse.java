package docker.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageListResponse {
    private boolean status;
    private String message;
    private Integer size;
    private List<ImageInfo> images;
}
