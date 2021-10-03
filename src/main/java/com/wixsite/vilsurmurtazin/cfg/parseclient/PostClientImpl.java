package com.wixsite.vilsurmurtazin.cfg.parseclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import com.wixsite.vilsurmurtazin.cfg.parseclient.dto.PostParse;




import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostClientImpl implements PostClient {

    private static Document getPage() throws IOException {
        String url = "https://playisgame.com/halyava/";
        return Jsoup.parse(new URL(url), 60000);
    }


    @Override
    public List<PostParse> findNewPosts() throws IOException {

        String href = "href";
        String img = "img";
        String title = "title";

        List<PostParse> lastPosts = new ArrayList<>();
        Document page = getPage();
        Elements selectDivClass = page.select(("div[class=pp-post-thumbnail-wrap]"));

        for (Element info : selectDivClass) {
            href = info.getElementsByTag("a").attr("href");
            img = info.getElementsByTag("a").select("img[src]").attr("src");
            title = info.getElementsByTag("a").select("img[src]").attr("title");

            while (!(href.isBlank() || img.isBlank() || title.isBlank())) {
                lastPosts.add(new PostParse(href, img, title));
                break;
            }
        }
        return lastPosts;
    }
}

