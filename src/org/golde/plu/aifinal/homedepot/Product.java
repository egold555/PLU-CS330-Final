package org.golde.plu.aifinal.homedepot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Product {

    final String name;
    final String description;
    final String imageURL;
}
