package com.abs.news.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
public class ApplicationPermissionEvaluator implements PermissionEvaluator {

//    @Autowired
//    private ClientCredentials clientCode;
//
//    @Autowired
//    private ESignTransactionRepository eSignTransactionRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object transactionId, Object methodType) {
//        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
//            Optional<ESignTransactionEntity> existingTransactionEntity = eSignTransactionRepository.findById(transactionId.toString());
//            if (!existingTransactionEntity.isEmpty()) {
//                ESignTransactionEntity transactionEntity = existingTransactionEntity.get();
//                if (transactionEntity.getClientCode().equals(clientCode.getClientId())) {
//                    return true;
//                }
//            }
//        }
        return false;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
