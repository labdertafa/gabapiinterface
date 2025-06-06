package com.laboratorio.gabapiinterface.impl;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.model.GabNotification;
import com.laboratorio.gabapiinterface.model.response.GabNotificationListResponse;
import java.util.List;
import com.laboratorio.gabapiinterface.GabNotificationApi;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 11/09/2024
 * @updated 06/06/2025
 */
public class GabNotificationApiImpl extends GabBaseApi implements GabNotificationApi {
    public GabNotificationApiImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public GabNotificationListResponse getAllNotifications() throws Exception {
        return this.getAllNotifications(0);
    }

    @Override
    public GabNotificationListResponse getAllNotifications(int limit) throws Exception {
        return this.getAllNotifications(limit, 0);
    }

    @Override
    public GabNotificationListResponse getAllNotifications(int limit, int quantity) throws Exception {
        return this.getAllNotifications(limit, quantity, null);
    }
    
    // Función que devuelve una página de notificaciones de una cuenta
    private GabNotificationListResponse getNotificationPage(String uri, int limit, int okStatus, String posicionInicial) throws Exception {
        try {
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.GET);
            request.addApiPathParam("limit", Integer.toString(limit));
            request.addApiPathParam("min_id", posicionInicial);
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            String minId = posicionInicial;
            List<GabNotification> notifications = gson.fromJson(response.getResponseStr(), new TypeToken<List<GabNotification>>(){}.getType());
            if (!notifications.isEmpty()) {
                log.debug("Se ejecutó la query: " + uri);
                log.debug("Resultados encontrados: " + notifications.size());

                List<String> linkHeaderList = response.getHttpHeaders().get("link");
                if ((linkHeaderList != null) && (!linkHeaderList.isEmpty())) {
                    String linkHeader = linkHeaderList.get(0);
                    log.debug("Recibí este link: " + linkHeader);
                    minId = this.extractMinId(linkHeader);
                    log.debug("Valor del min_id: " + minId);
                }
            }

            // return accounts;
            return new GabNotificationListResponse(minId, notifications);
        } catch (Exception e) {
            throw new GabApiException("Error recuperando una página de notificaciones de Gab", e);
        }
    }

    @Override
    public GabNotificationListResponse getAllNotifications(int limit, int quantity, String posicionInicial) throws Exception {
        String endpoint = this.apiConfig.getProperty("getNotifications_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getNotifications_ok_status"));
        int defaultLimit = Integer.parseInt(this.apiConfig.getProperty("getNotifications_default_limit"));
        int maxLimit = Integer.parseInt(this.apiConfig.getProperty("getNotifications_max_limit"));
        int usedLimit = limit;
        if ((limit == 0) || (limit > maxLimit)) {
            usedLimit = defaultLimit;
        }
        List<GabNotification> notifications = null;
        boolean continuar = true;
        String min_id = "0";
        if (posicionInicial != null) {
            min_id = posicionInicial;
        }
        
        if (quantity > 0) {
            usedLimit = Math.min(usedLimit, quantity);
        }
        
        try {
            do {
                GabNotificationListResponse notificationListResponse = this.getNotificationPage(endpoint, usedLimit, okStatus, min_id);
                if (notifications == null) {
                    notifications = notificationListResponse.getNotifications();
                } else {
                    notifications.addAll(notificationListResponse.getNotifications());
                }
                
                min_id = notificationListResponse.getMinId();
                log.debug("getFollowers. Cantidad: " + quantity + ". Recuperados: " + notifications.size() + ". Min_id: " + min_id);
                if (notificationListResponse.getNotifications().isEmpty()) {
                    continuar = false;
                } else {
                    if (quantity > 0) {
                        if (notifications.size() >= quantity) {
                            continuar = false;
                        }
                    } else {
                        if (notificationListResponse.getNotifications().size() < usedLimit) {
                            continuar = false;
                        }
                    }
                }
            } while (continuar);

            if (quantity == 0) {
                return new GabNotificationListResponse(min_id, notifications);
            }
            
            return new GabNotificationListResponse(min_id, notifications.subList(0, Math.min(quantity, notifications.size())));
        } catch (Exception e) {
            throw e;
        }
    }
}