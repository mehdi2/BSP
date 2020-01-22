package bsp.service;

import bsp.model.AuthRequestMap;
import bsp.model.AuthUser;

import java.math.BigInteger;
import java.util.List;

public interface AuthRequestMapService {
    List<AuthRequestMap> findAll();

    AuthRequestMap findById(BigInteger id);

    AuthRequestMap save(AuthRequestMap authRequestMap);

    void delete(BigInteger pid);
}
