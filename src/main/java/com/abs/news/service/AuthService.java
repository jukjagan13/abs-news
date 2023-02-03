//package com.abs.news.service;
//
//import com.abs.news.entity.ESignClientsEntity;
//import com.abs.news.exception.ValidationError;
//import com.abs.news.exception.ValidationException;
//import com.abs.news.model.Client;
//import com.abs.news.model.ClientCredentials;
//import com.abs.news.repository.ESignClientsRepository;
//import com.abs.news.utilities.CommonUtilities;
//import com.abs.news.utilities.JwtProvider;
//import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.util.*;
//
//@Service
//public class AuthService {
//    @Autowired
//    CommonUtilities commonUtils;
//    @Autowired
//    JwtProvider jwtProvider;
//
//    @Autowired
//    ESignClientsRepository eSignClientsRepository;
//    @Value("${app.private-key:}")
//    private String privateKey;
//
//    @Autowired
//    private ClientCredentials clientCode;
//
//    /**
//     * @param publicKey
//     * @return
//     */
//    public ClientCredentials generateNewClient(String publicKey) {
//        if (!commonUtils.matchKeys(privateKey, publicKey)) {
//            throw new ValidationException(new ValidationError("Key", "Keys dont match"));
//        }
//        String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
//        String clientId = commonUtils.hashStringSHA26(UUID.randomUUID().toString());
//        String encoded = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
//        String clientSecret = commonUtils.hashStringSHA26(encoded);
//        String clientSecretEncrypted = commonUtils.hashStringBCrypt(clientSecret);
//
//        Calendar cal = Calendar.getInstance();
//        Date cdate = cal.getTime();
//        // get next year date
//        cal.add(Calendar.YEAR, 1);
//        Date nyear = cal.getTime();
//
//        ESignClientsEntity newClient = new ESignClientsEntity();
//        newClient.setClientCode(UUID.randomUUID().toString());
//        newClient.setClientId(clientId);
//        newClient.setClientSecret(clientSecretEncrypted);
//        newClient.setLastUpdatedTs(timeStamp);
//        newClient.setCreatedTs(timeStamp);
//        newClient.setEffectiveFromTs(new Timestamp(cdate.getTime()).toString());
//        newClient.setEffectiveToTs(new Timestamp(nyear.getTime()).toString());
//        eSignClientsRepository.save(newClient);
//
//        ClientCredentials credentials = new ClientCredentials();
//        credentials.setClientId(clientId);
//        credentials.setClientSecret(encoded);
//
//        return credentials;
//    }
//
//    public String verifyClient(ClientCredentials payload) {
//        ESignClientsEntity clientDetails = eSignClientsRepository.findByClientId(payload.getClientId());
//        if (clientDetails != null) {
//            String hashed = commonUtils.hashStringSHA26(payload.getClientSecret());
//            String providedSecret = commonUtils.hashStringBCrypt(hashed);
//            String secret = clientDetails.getClientSecret();
//            Timestamp endTs = Timestamp.valueOf(clientDetails.getEffectiveToTs());
//            Timestamp currTs = new Timestamp(System.currentTimeMillis());
//            if (providedSecret.equals(secret) && endTs.compareTo(currTs) > 0) {
//                Map<String, Object> claims = new HashMap<>();
//                String accessToken = jwtProvider.generateAccessToken(clientDetails.getClientCode(), claims);
////                Map<String, String> response = new HashMap<>();
////                response.put("accessToken", accessToken);
////                return response;
//                return accessToken;
//            }
//        }
//        return null;
//    }
//
//    public Client updateClientDetails(Client clientDetails) {
//        ModelMapper modelMapper = new ModelMapper();
//        ESignClientsEntity client = eSignClientsRepository.findByClientCode(clientCode.getClientId());
//        if (client != null) {
//            if (StringUtils.isNoneEmpty(clientDetails.getClientNote())) {
//                client.setClientNote(clientDetails.getClientNote());
//            }
//            if (StringUtils.isNoneEmpty(clientDetails.getClientName())) {
//                client.setClientName(clientDetails.getClientName());
//            }
//            eSignClientsRepository.save(client);
//            return modelMapper.map(client, Client.class);
//        }
//        return null;
//    }
//}
