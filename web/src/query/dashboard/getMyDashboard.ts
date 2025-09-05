import { getMyDashboard } from '@/app/api/dashboard';
import type { Dashboard } from '@/types/dashboard';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Dashboard;
type TError = KiokeError;

function key() {
  return ['dashboards', 'me'] as const;
}

function options(): UseQueryOptions<TData, TError> {
  return {
    queryKey: key(),
    queryFn: async () => getMyDashboard().then((res) => unwrap(res)),
  };
}

function useMyDashboardQuery() {
  return useQuery(options());
}

export {
  useMyDashboardQuery,
  key as myDashboardQueryKey,
  options as myDashboardQueryOptions,
};
