package br.com.olua.persistence.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_product")
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "category_id", nullable = false)
    private String category;

    @Column(name = "price", nullable = false)
    private Double price;

}
