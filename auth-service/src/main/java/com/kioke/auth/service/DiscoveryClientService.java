package com.kioke.auth.service;

import com.kioke.auth.constant.KiokeServices;
import com.kioke.auth.exception.ServiceNotFoundException;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryClientService {
  @Autowired @Lazy private DiscoveryClient discoveryClient;

  private Random random = new Random();

  public String getServiceUri(KiokeServices service) throws ServiceNotFoundException {
    List<ServiceInstance> instances = discoveryClient.getInstances(service.getServiceId());
    if (instances.size() == 0) {
      throw new ServiceNotFoundException(service);
    }

    int index = random.nextInt(instances.size());
    ServiceInstance instance = instances.get(index);

    return instance.getUri().toString();
  }
}
