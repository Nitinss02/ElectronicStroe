package com.electroinc.store.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;

    @NotBlank(message = "Title Is Requried")
    private String title;
    @NotBlank(message = "Description is Requried")
    private String description;
    private String coverImage;
}
