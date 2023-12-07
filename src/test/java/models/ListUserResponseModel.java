package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListUserResponseModel {

    int page;
    @JsonProperty("per_page")
    int perPage;
    int total;
    @JsonProperty("total_pages")
    int totalPages;
    List<DataList> data;
    Support support;

    @Data
    public static class DataList {
        int id;
        String email;
        @JsonProperty("first_name")
        String firstName;
        @JsonProperty("last_name")
        String lastName;
        String avatar;
    }

    @Data
    public static class Support {
        String url, text;
    }
}
