package com.laboratorio.gabapiinterface.impl;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.model.GabMediaAttachment;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.gabapiinterface.GabStatusApi;
import com.laboratorio.gabapiinterface.model.response.GabStatusListResponse;
import com.laboratorio.gabapiinterface.model.tl.GabGroupsTlResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rafael
 * @version 1.4
 * @created 12/09/2024
 * @updated 06/06/2025
 */
public class GabStatusApiImpl extends GabBaseApi implements GabStatusApi {
    public GabStatusApiImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public GabStatus postStatus(String text) {
        return this.postStatus(text, null);
    }

    @Override
    public GabStatus postStatusWithImage(String text, GabMediaAttachment mediaAttachment) {
        String endpoint = this.apiConfig.getProperty("postStatus_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("postStatus_ok_status"));
        
        try {
            String uri = endpoint;
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.POST);
            request.addApiPathParam("status", text);
            request.addApiPathParam("visibility", "public");
            request.addApiPathParam("language", "es");
            if (mediaAttachment != null) {
                request.addApiPathParam("media_ids[]", mediaAttachment.getId());
            }
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response postStatusWithImage: {}", response.getResponseStr());
            
            return this.gson.fromJson(response.getResponseStr(), GabStatus.class);
        } catch (Exception e) {
            throw new GabApiException("Error posteando un estado en gab: " + text, e);
        }
    }
    
    @Override
    public GabStatus postStatus(String text, String filePath) {
        if (filePath != null) {
            GabMediaAttachment mediaAttachment = this.uploadImage(filePath);
            return this.postStatusWithImage(text, mediaAttachment);
        }

        return this.postStatusWithImage(text, null);
    }

    @Override
    public GabMediaAttachment uploadImage(String filePath) {
        String endpoint = this.apiConfig.getProperty("UploadImage_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("UploadImage_ok_status"));
        
        try {
            String uri = endpoint;
            
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.POST);
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            request.addFileFormData("file", filePath);
                        
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response uploadImage: {}", response.getResponseStr());
            
            return this.gson.fromJson(response.getResponseStr(), GabMediaAttachment.class);
        } catch (Exception e) {
            throw new GabApiException("Error subiendo una imagen a gab: " + filePath, e);
        }
    }

    @Override
    public boolean deleteStatus(String id) {
        String endpoint = this.apiConfig.getProperty("deleteStatus_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("deleteStatus_ok_status"));
        
        try {
            String uri = endpoint + "/" + id;
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.DELETE);
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            this.client.executeApiRequest(request);
            
            return true;
        } catch (Exception e) {
            throw new GabApiException("Error eliminado el estado en gab con id: " + id, e);
        }
    }
    
    private String getNextPageLink(String input) {
        // Expresión regular para buscar la URL de "rel=next"
        String regex = "<([^>]+)>;\\s*rel=\"next\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }
    
    private GabStatusListResponse getPersonalTimelinePage(String uri, int okStatus, String nextPage) {
        try {
            ApiRequest request;
            if (nextPage == null) {
                request = new ApiRequest(uri, okStatus, ApiMethodType.GET);
            } else {
                request = new ApiRequest(nextPage, okStatus, ApiMethodType.GET);
            }
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            List<GabStatus> statuses = this.gson.fromJson(response.getResponseStr(), new TypeToken<List<GabStatus>>(){}.getType());
            String newNextPage = null;
            if (!statuses.isEmpty()) {
                log.debug("Se ejecutó la query: " + uri);
                log.debug("Resultados encontrados: " + statuses.size());

                List<String> linkHeaderList = response.getHttpHeaders().get("link");
                if ((linkHeaderList != null) && (!linkHeaderList.isEmpty())) {
                    String linkHeader = linkHeaderList.get(0);
                    log.debug("Recibí este link: " + linkHeader);
                    newNextPage = this.getNextPageLink(linkHeader);
                    log.debug("Valor del newNextPage: " + newNextPage);
                }
            }

            return new GabStatusListResponse(statuses, newNextPage);
        } catch (Exception e) {
            throw new GabApiException("Error recuperando una página del Timeline personal de Gab", e);
        }
    }

    @Override
    public List<GabStatus> getPersonalTimeline(int quantity) {
        String endpoint = this.apiConfig.getProperty("getPersonalTimeLine_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getPersonalTimeLine_ok_status"));
        
        List<GabStatus> statuses = null;
        boolean continuar = true;
        String nextPage = null;
        
        try {
            String uri = endpoint;
            
            do {
                GabStatusListResponse statusListResponse = this.getPersonalTimelinePage(uri, okStatus, nextPage);
                log.debug("Elementos recuperados total: " + statusListResponse.getStatuses().size());
                if (statuses == null) {
                    statuses = statusListResponse.getStatuses();
                } else {
                    statuses.addAll(statusListResponse.getStatuses());
                }
                
                nextPage = statusListResponse.getNextPage();
                log.debug("getGlobalTimeline. Recuperados: " + statuses.size() + ". Next page: " + nextPage);
                if (statusListResponse.getStatuses().isEmpty()) {
                    continuar = false;
                } else {
                    if ((nextPage == null) || (statuses.size() >= quantity)) {
                        continuar = false;
                    }
                }
            } while (continuar);
            
            return statuses.subList(0, Math.min(quantity, statuses.size()));
        } catch (Exception e) {
            throw e;
        }
    }
    
    private GabGroupsTlResponse getTimelinePage(String uri, int okStatus, int pageNum) {
        try {
            ApiRequest request;
            request = new ApiRequest(uri, okStatus, ApiMethodType.GET);
            request.addApiPathParam("page", Integer.toString(pageNum));
            request.addApiPathParam("sort_by", "newest");
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response getTimelinePage: {}", response.getResponseStr());

            return this.gson.fromJson(response.getResponseStr(), GabGroupsTlResponse.class);
        } catch (Exception e) {
            throw new GabApiException("Error recuperando una página del Timeline global de Gab", e);
        }
    }

    @Override
    public List<GabStatus> getGlobalTimeline(int quantity) {
        String endpoint = this.apiConfig.getProperty("getGlobalTimeLine_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getGlobalTimeLine_ok_status"));
        
        List<GabStatus> statuses = null;
        boolean continuar = true;
        int nextPage = 1;
        
        try {
            String uri = endpoint;
            
            do {
                GabGroupsTlResponse response = this.getTimelinePage(uri, okStatus, nextPage);
                log.debug("Elementos recuperados en la página: " + response.getS().size());
                if (statuses == null) {
                    statuses = response.getStatusList();
                } else {
                    statuses.addAll(response.getStatusList());
                }
                
                nextPage++;
                log.debug("getGlobalTimeline. Recuperados: " + statuses.size() + ". Next page: " + nextPage);
                if (response.getS().isEmpty()) {
                    continuar = false;
                } else {
                    if (statuses.size() >= quantity) {
                        continuar = false;
                    }
                }
            } while (continuar);
            
            return statuses.subList(0, Math.min(quantity, statuses.size()));
        } catch (Exception e) {
            throw e;
        }
    }
}