package com.wixsite.vilsurmurtazin.cfg.service;

import com.wixsite.vilsurmurtazin.cfg.parseclient.PostClient;
import com.wixsite.vilsurmurtazin.cfg.repository.PostRepository;
import com.wixsite.vilsurmurtazin.cfg.repository.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FindNewPostsServiceImpl implements FindNewPostsService {


    public List<Post> newPosts= new ArrayList<>();
    private final PostRepository postRepository;
    private final TelegramUserService telegramUserService;
    private final PostClient postClient;
    private final SendBotMessageService sendMessageService;
    Post postMidl;

    @Autowired
    public FindNewPostsServiceImpl(PostRepository postRepository,
                                   TelegramUserService telegramUserService,
                                   PostClient postClient,
                                   SendBotMessageService sendMessageService) {
        this.postRepository = postRepository;   // из базы посты
        this.telegramUserService = telegramUserService;   // из базы изеры
        this.postClient = postClient;    // из сайта парсером
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewPosts() throws IOException {

        postClient.findNewPosts().forEach(postParse -> {
            Optional<Post> postFromDB = postRepository.findByHref(postParse.getHref());
            if (!postFromDB.isPresent()) {
                postMidl = new Post();
                postMidl.setHref(postParse.getHref());
                postMidl.setTitle(postParse.getTitle());
                postMidl.setLinkImage(postParse.getLinkImage());
                postRepository.save(postMidl);
                newPosts.add(postMidl);
            }
        });
            notifySubscribersAboutNewPosts(newPosts);

    }

    private void notifySubscribersAboutNewPosts(List<Post> newPosts) {
        List<String> messagesWithNewPosts = newPosts.stream()
                .map(newPost -> String.format("✨Вышла новая новость <b>%s</b> .✨\n\n" +
                                "<b>Ссылка</b> %s\n",
                        newPost.getTitle(), newPost.getHref()))
                .collect(Collectors.toList());

        telegramUserService.findAllActiveUsers().
                forEach(it -> sendMessageService.sendMessage(it.getChatId(), messagesWithNewPosts));

        newPosts.clear();
    }

}