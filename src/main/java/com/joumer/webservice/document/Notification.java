package com.joumer.webservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Notification {

    @Id
    private String id;
    private String description;
    private Date dueDate;


    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
