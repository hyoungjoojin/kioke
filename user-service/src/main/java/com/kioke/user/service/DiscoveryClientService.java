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

  public static enum KiokeService {
    JOURNAL("journal");

    private String serviceId;

    private KiokeService(String serviceId) {
      this.serviceId = serviceId;
    }

    public String getServiceId() {
      return serviceId;
    }
  }

  private Random random = new Random();

  public URI getServiceUri(KiokeService service) throws ServiceNotFoundException {
    var instances = discoveryClient.getInstances(service.getServiceId());
    if (instances.isEmpty()) {
      throw new ServiceNotFoundException(service.getServiceId());
    }

    return instances.get(random.nextInt(instances.size())).getUri();
  }
}
