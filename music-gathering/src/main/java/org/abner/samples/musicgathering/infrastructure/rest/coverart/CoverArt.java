package org.abner.samples.musicgathering.infrastructure.rest.coverart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Data
public class CoverArt {

    private List<Images> images;

    public CoverArt() {
        this.images = new ArrayList<>();
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    //    @Data
    public static class Images {
        private String image;
        private boolean front;
        private boolean back;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isFront() {
            return front;
        }

        public void setFront(boolean front) {
            this.front = front;
        }

        public boolean isBack() {
            return back;
        }

        public void setBack(boolean back) {
            this.back = back;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Images images = (Images) o;
            return front == images.front &&
                    back == images.back &&
                    image.equals(images.image);
        }

        @Override
        public int hashCode() {
            return Objects.hash(image, front, back);
        }
    }

}
