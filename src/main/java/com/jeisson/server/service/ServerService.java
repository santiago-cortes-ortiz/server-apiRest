package com.jeisson.server.service;

import com.jeisson.server.model.Server;

import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}
