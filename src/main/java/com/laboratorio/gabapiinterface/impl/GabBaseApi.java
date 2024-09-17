package com.laboratorio.gabapiinterface.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.impl.ApiClientImpl;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ProcessedResponse;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.response.GabAccountListResponse;
import com.laboratorio.gabapiinterface.utils.GabApiConfig;
import com.laboratorio.gabapiinterface.utils.InstruccionInfo;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 11/09/2024
 * @updated 11/09/2024
 */
public class GabBaseApi {
    protected static final Logger log = LogManager.getLogger(GabBaseApi.class);
    protected final ApiClient client;
    protected final String accessToken;
    protected GabApiConfig apiConfig;
    protected final Gson gson;
    
    public GabBaseApi(String accessToken) {
        this.client = new ApiClientImpl();
        this.accessToken = accessToken;
        this.apiConfig = GabApiConfig.getInstance();
        this.gson = new Gson();
    }
    
    protected void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    // Función que extrae el max_id de la respuesta
    protected String extractMaxId(String str) {
        String maxId = null;
        String regex = "max_id=(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        
        if (matcher.find()) {
            maxId = matcher.group(1); // El primer grupo de captura contiene el valor de max_id
        }
        
        return maxId;
    }
    
    // Función que extrae el min_id de la respuesta
    protected String extractMinId(String str) {
        String maxId = null;
        String regex = "min_id=(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        
        if (matcher.find()) {
            maxId = matcher.group(1); // El primer grupo de captura contiene el valor de max_id
        }
        
        return maxId;
    }
    
    // Función que devuelve una página de seguidores o seguidos de una cuenta
    private GabAccountListResponse getAccountPage(String uri, int okStatus, int limit, String posicionInicial) throws Exception {
        try {
            ApiRequest request = new ApiRequest(uri, okStatus);
            request.addApiPathParam("limit", Integer.toString(limit));
            if (posicionInicial != null) {
                request.addApiPathParam("max_id", posicionInicial);
            }
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ProcessedResponse response = this.client.getProcessedResponseGetRequest(request);
            
            List<GabAccount> accounts = this.gson.fromJson(response.getResponseDetail(), new TypeToken<List<GabAccount>>(){}.getType());
            String maxId = null;
            if (!accounts.isEmpty()) {
                log.debug("Se ejecutó la query: " + uri);
                log.debug("Resultados encontrados: " + accounts.size());

                String linkHeader = response.getResponse().getHeaderString("link");
                log.debug("Recibí este link: " + linkHeader);
                maxId = this.extractMaxId(linkHeader);
                log.debug("Valor del max_id: " + maxId);
            }

            // return accounts;
            return new GabAccountListResponse(maxId, accounts);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (Exception e) {
            throw new GabApiException(GabBaseApi.class.getName(), e.getMessage());
        }
    }
    
    protected GabAccountListResponse getCounterAccountList(InstruccionInfo instruccionInfo, String userId, int quantity, String posicionInicial) throws Exception {
        List<GabAccount> accounts = null;
        boolean continuar = true;
        String endpoint = instruccionInfo.getEndpoint();
        String complemento = instruccionInfo.getComplementoUrl();
        int limit = instruccionInfo.getLimit();
        int okStatus = instruccionInfo.getOkStatus();
        String max_id = posicionInicial;
        
        if (quantity > 0) {
            limit = Math.min(limit, quantity);
        }
        
        String uri = endpoint + "/" + userId + "/" + complemento;
        
        try {
            do {
                GabAccountListResponse accountListResponse = this.getAccountPage(uri, okStatus, limit, max_id);
                if (accounts == null) {
                    accounts = accountListResponse.getAccounts();
                } else {
                    accounts.addAll(accountListResponse.getAccounts());
                }
                
                max_id = accountListResponse.getMaxId();
                log.debug("getMastodonAccountList. Cantidad: " + quantity + ". Recuperados: " + accounts.size() + ". Max_id: " + max_id);
                if (quantity > 0) {
                    if ((accounts.size() >= quantity) || (max_id == null)) {
                        continuar = false;
                    }
                } else {
                    if ((max_id == null) || (accountListResponse.getAccounts().size() < limit)) {
                        continuar = false;
                    }
                }
            } while (continuar);

            if (quantity == 0) {
                return new GabAccountListResponse(max_id, accounts);
            }
            
            return new GabAccountListResponse(max_id, accounts.subList(0, Math.min(quantity, accounts.size())));
        } catch (Exception e) {
            throw e;
        }
    }
}