import { ErrorCode } from '@/constant/error';
import KiokeError from '@/util/error';
import { QueryClient, isServer } from '@tanstack/react-query';

function createQueryClient() {
  return new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 60 * 1000,
        retryDelay: (failureCount) => {
          return 1000 * failureCount;
        },
        retry: (failureCount, error) => {
          if (
            error instanceof KiokeError &&
            error.code !== ErrorCode.NETWORK_REQUEST_FAILED
          ) {
            return false;
          }

          return failureCount < 3;
        },
      },
    },
  });
}

let browserQueryClient: QueryClient | undefined = undefined;

export function getQueryClient() {
  if (isServer) {
    return createQueryClient();
  }

  if (!browserQueryClient) {
    browserQueryClient = createQueryClient();
  }
  return browserQueryClient;
}
