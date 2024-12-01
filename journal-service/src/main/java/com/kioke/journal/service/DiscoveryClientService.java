package com.kioke.journal.service;

import com.kioke.journal.constant.KiokeServices;
import com.kioke.journal.exception.ServiceFailedException;
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
import org.springframework.web.client.RestClient;

@Service
public class DiscoveryClientService {
  @Autowired @Lazy DiscoveryClient discoveryClient;

  @Retryable(
      retryFor = ServiceFailedException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 100))
  public <T> RestClient getRestClient(KiokeServices service, String path)
      throws ServiceFailedException, ServiceNotFoundException {
    String requestUri = getServiceUri(service) + path;
    return RestClient.create(requestUri);
  }

  @Retryable(
      retryFor = ServiceNotFoundException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 100))
  private String getServiceUri(KiokeServices service) throws ServiceNotFoundException {
    List<ServiceInstance> instances = discoveryClient.getInstances(service.getServiceId());
    if (instances.size() == 0) {
      throw new ServiceNotFoundException(service);
    }

    int index = getRandomIndex(instances.size());
    ServiceInstance instance = instances.get(index);

    return instance.getUri().toString();
  }

  private static int getRandomIndex(int size) {
    Random random = new Random();
    return random.nextInt(size);
  }
}
