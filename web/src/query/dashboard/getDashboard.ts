import { getDashboard } from '@/app/api/dashboard';
import type { Dashboard } from '@/types/dashboard';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Dashboard;
type TError = KiokeError;

type Params = {
  userId: string;
};

function key({ userId }: Params) {
  return ['dashboards', userId] as const;
}

function options(params: Params): UseQueryOptions<TData, TError> {
  return {
    queryKey: key(params),
    queryFn: async () => getDashboard(params).then((res) => unwrap(res)),
  };
}

function useDashboardQuery(params: Params) {
  return useQuery(options(params));
}

export {
  useDashboardQuery,
  key as dashboardQueryKey,
  options as dashboardQueryOptions,
};
