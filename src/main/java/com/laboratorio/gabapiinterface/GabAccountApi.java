package com.laboratorio.gabapiinterface;

import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabRelationship;
import com.laboratorio.gabapiinterface.model.response.GabAccountListResponse;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 05/09/2024
 * @updated 12/09/2024
 */
public interface GabAccountApi {
    GabAccount getAccountById(String userId);
    
    GabAccountListResponse getFollowers(String userId) throws Exception;
    GabAccountListResponse getFollowers(String id, int limit) throws Exception;
    GabAccountListResponse getFollowers(String userId, int limit, int quantity) throws Exception;
    GabAccountListResponse getFollowers(String userId, int limit, int quantity, String posicionInicial) throws Exception;
    
    GabAccountListResponse getFollowings(String userId) throws Exception;
    GabAccountListResponse getFollowings(String id, int limit) throws Exception;
    GabAccountListResponse getFollowings(String userId, int limit, int quantity) throws Exception;
    GabAccountListResponse getFollowings(String userId, int limit, int quantity, String posicionInicial) throws Exception;
    
    // Seguir a un usuario
    boolean followAccount(String userId);
    // Dejar de seguir a un usuario
    boolean unfollowAccount(String userId);
    
    // Chequea la relación con otro usuario
    GabRelationship checkrelationships(String userId);
}