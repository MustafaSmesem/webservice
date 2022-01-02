package com.joumer.webservice.document.records;

public record JwtResponse(String id, String username, String fullName, String token, boolean admin){}
