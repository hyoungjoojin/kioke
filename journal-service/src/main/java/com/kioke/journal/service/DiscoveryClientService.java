package com.kioke.journal.service;

import com.kioke.journal.constant.KiokeServices;
import com.kioke.journal.exception.ServiceNotFoundException;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryClientService {
  @Autowired @Lazy private DiscoveryClient discoveryClient;

  private Random random = new Random();

  @Retryable(
      retryFor = ServiceNotFoundException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 100))
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
