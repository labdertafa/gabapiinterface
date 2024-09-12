package com.laboratorio.gabapiinterface;

import com.laboratorio.gabapiinterface.model.GabMediaAttachment;
import com.laboratorio.gabapiinterface.model.GabStatus;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 07/09/2024
 * @updated 12/09/2024
 */
public interface GabStatusApi {
    GabStatus postStatus(String text);
    GabStatus postStatus(String text, String imagenId);
    GabMediaAttachment uploadImage(String filePath);
}