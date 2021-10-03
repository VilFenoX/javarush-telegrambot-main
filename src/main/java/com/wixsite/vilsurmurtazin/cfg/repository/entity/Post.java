package com.wixsite.vilsurmurtazin.cfg.repository.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;


@Data
@Entity
@Table(name = "posts")
@EqualsAndHashCode(exclude = "user")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "href")
    private String href;

    @Column(name = "link_image")
    private String linkImage;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private TelegramUser user;


}
