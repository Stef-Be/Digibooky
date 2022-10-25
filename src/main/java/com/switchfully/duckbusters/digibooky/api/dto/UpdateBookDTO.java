package com.switchfully.duckbusters.digibooky.api.dto;

public class UpdateBookDTO {

    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String summary;

    public UpdateBookDTO(String title, String authorFirstName, String authorLastName, String summary) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getSummary() {
        return summary;
    }
}
