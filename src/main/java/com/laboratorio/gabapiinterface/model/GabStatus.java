package com.laboratorio.gabapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 24/07/2024
 * @updated 25/07/2024
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
    // private Poll poll;
    // private PreviewCard card;
    private String language;
    private String text;
    private String edited_at;
    private boolean favourited;
    private boolean reblogged;
    private boolean muted;
    private boolean bookmarked;
    private boolean pinned;
    private GabFilterResult[] filtered;
}