import { getNotifications } from '@/app/api/notification';
import type { Notification } from '@/types/notification';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseQueryOptions, useQuery } from '@tanstack/react-query';

type TData = Notification[];
type TError = KiokeError;

type Options = UseQueryOptions<TData, TError>;

function key() {
  return ['notifications'] as const;
}

function options(): Options {
  return {
    queryKey: key(),
    queryFn: async () =>
      getNotifications().then((response) => unwrap(response)),
  };
}

function useNotificationsQuery() {
  return useQuery(options());
}

export {
  useNotificationsQuery,
  key as notificationsQueryKey,
  options as notificationsQueryOptions,
};
