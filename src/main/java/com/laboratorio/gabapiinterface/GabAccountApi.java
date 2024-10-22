package com.laboratorio.gabapiinterface;

import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabRelationship;
import com.laboratorio.gabapiinterface.model.GabSuggestionType;
import com.laboratorio.gabapiinterface.model.response.GabAccountListResponse;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 05/09/2024
 * @updated 22/10/2024
 */
public interface GabAccountApi {
    GabAccount getAccountById(String userId);
    
    GabAccountListResponse getFollowers(String userId) throws Exception;
    GabAccountListResponse getFollowers(String userId, int limit) throws Exception;
    GabAccountListResponse getFollowers(String userId, int limit, int quantity) throws Exception;
    GabAccountListResponse getFollowers(String userId, int limit, int quantity, String posicionInicial) throws Exception;
    
    List<String> getFollowersIds(String userId, int limit) throws Exception;
    
    GabAccountListResponse getFollowings(String userId) throws Exception;
    GabAccountListResponse getFollowings(String userId, int limit) throws Exception;
    GabAccountListResponse getFollowings(String userId, int limit, int quantity) throws Exception;
    GabAccountListResponse getFollowings(String userId, int limit, int quantity, String posicionInicial) throws Exception;
    
    List<String> getFollowingsIds(String userId, int limit) throws Exception;
    
    // Seguir a un usuario
    boolean followAccount(String userId);
    // Dejar de seguir a un usuario
    boolean unfollowAccount(String userId);
    
    // Chequea la relación con otro usuario
    List<GabRelationship> checkrelationships(List<String> usersId);
    
    // Obtiene el listado de sugerencias de seguimiento para el usuario
    List<GabAccount> getSuggestions(GabSuggestionType type);
}