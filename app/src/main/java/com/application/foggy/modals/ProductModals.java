package com.application.foggy.modals;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductModals implements Serializable {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Boolean active;
    private String createdUserName;
    private String createdUserEmail;
    private String createdOn;
    private String lastUpdatedUserName;
    private String lastUpdatedEmail;
    private String lastUpdatedOn;
}
