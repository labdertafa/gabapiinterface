package com.laboratorio.gabapiinterface.model;

import com.laboratorio.gabapiinterface.model.tl.GabGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 24/07/2024
 * @updated 24/10/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GabStatus {
    private String id;
    private String uri;
    private String created_at;
    private GabAccount account;
    private String content;
    private String visibility;
    private boolean sensitive;
    private String spoiler_text;
    private GabMediaAttachment[] media_attachments;
    private GabApplication application;
    private GabMention[] mentions;
    private GabStatusTag[] tags;
    private GabCustomEmoji[] emojis;
    private int reblogs_count;
    private int favourites_count;
    private int replies_count;
    private String url;
    private String in_reply_to_id;
    private String in_reply_to_account_id;
    private GabReblog reblog;
    private String language;
    private String text;
    private String edited_at;
    private boolean favourited;
    private boolean reblogged;
    private boolean muted;
    private boolean bookmarked;
    private boolean pinned;
    private GabFilterResult[] filtered;
    
    public GabStatus(GabGroupStatus status) {
        this.id = status.getI();
        this.uri = status.getUl();
        this.created_at = status.getCa();
        this.content = status.getC();
        this.visibility = status.getV();
        this.sensitive = status.isS();
        this.spoiler_text = status.getSt();
        this.reblogs_count = status.getRbc();
        this.favourites_count = status.getFc();
        this.replies_count = status.getRc();
        this.url = status.getU();
        this.language = status.getL();
        this.edited_at = status.getCa();
        this.favourited = status.isFvd();
        this.reblogged = status.isRbgd();
    }

    @Override
    public String toString() {
        return "GabStatus{" + "id=" + id + ", created_at=" + created_at + ", reblogs_count=" + reblogs_count + ", favourites_count=" + favourites_count + ", replies_count=" + replies_count + ", language=" + language + ", text=" + text + '}';
    }
}