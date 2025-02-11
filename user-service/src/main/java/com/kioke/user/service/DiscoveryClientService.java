package com.kioke.user.service;

import com.kioke.user.exception.discovery.ServiceNotFoundException;
import java.net.URI;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryClientService {
  @Autowired @Lazy DiscoveryClient discoveryClient;

  private Random random = new Random();

  public URI getServiceUri(String serviceId) throws ServiceNotFoundException {
    var instances = discoveryClient.getInstances(serviceId);
    if (instances.isEmpty()) {
      throw new ServiceNotFoundException(serviceId);
    }

    return instances.get(random.nextInt(instances.size())).getUri();
  }
}
