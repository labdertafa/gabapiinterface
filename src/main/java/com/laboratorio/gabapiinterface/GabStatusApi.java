package com.laboratorio.gabapiinterface;

import com.laboratorio.gabapiinterface.model.GabMediaAttachment;
import com.laboratorio.gabapiinterface.model.GabStatus;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 07/09/2024
 * @updated 06/10/2024
 */
public interface GabStatusApi {
    GabStatus postStatus(String text);
    GabStatus postStatusWithImage(String text, GabMediaAttachment mediaAttachment);
    GabStatus postStatus(String text, String filePath);
    GabMediaAttachment uploadImage(String filePath);
    boolean deleteStatus(String id);
}