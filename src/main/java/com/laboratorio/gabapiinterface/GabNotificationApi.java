package com.laboratorio.gabapiinterface;

import com.laboratorio.gabapiinterface.model.response.GabNotificationListResponse;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 11/09/2024
 * @updated 11/09/2024
 */
public interface GabNotificationApi {
    GabNotificationListResponse getAllNotifications() throws Exception;
    GabNotificationListResponse getAllNotifications(int limit) throws Exception;
    GabNotificationListResponse getAllNotifications(int limit, int quantity) throws Exception;
    GabNotificationListResponse getAllNotifications(int limit, int quantity, String posicionInicial) throws Exception;
}